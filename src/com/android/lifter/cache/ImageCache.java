package com.android.lifter.cache;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.app.ActivityManager;


/**
 * Cache do obrazkow bioracy pod uwage 
 * 
 */
public class ImageCache
{
    /**
     * instancja obiektu
     * 
     */
    private static ImageCache hInstance = null;
        
    /** 
     * context aplikacji potrzebny do wyliczania zajetosci pamieci
     * 
     */
    private Context coContext = null;
    
    /**
     * Rozmiary obrazkow
     * 
     */
    private final int IMAGE_WIDTH = 400;
    private final int IMAGE_HEIGHT = 258;
    
    /**
     * tabela haskujaca z obrazkami
     * 
     */
    private Hashtable<String,Bitmap> htCache = new Hashtable<String,Bitmap>();
    
    /**
     * rozmiar cache w bajtach
     * 
     */
    private int iCacheSize = 0;
    
    /**
     * maksymalny rozmiar cache w bajtach
     * 
     */
    private int iCacheMax = 0;
    
    /**
     * zwraca instancje singletona
     * 
     * @param coContext
     * @return
     */
    public static ImageCache instance(Context coContext)
    {
        hInstance = hInstance == null ? new ImageCache() : hInstance;
        hInstance.coContext = coContext;
        
        int iMemClass = ((ActivityManager) coContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        hInstance.iCacheMax = 1024 * 1024 * iMemClass / 8;
        
        return hInstance;
    }
    
    
    /** 
     * prywatny konstruktor, nie robi nic, siedzi i smierdzi
     * 
     */
    private ImageCache()
    {
    }
    
    @Override
    protected void finalize() throws Throwable
    {
        try
        {
            htCache.clear();
            htCache   = null;
            coContext = null;
            hInstance = null;
        }
        finally
        {
            super.finalize();
        }   
    }
    
    /**
     * produkuje miniaturke z lokalnych zdjec, filmow i zdalnych fotek
     * 
     * @param szPath adres url badz sciezka do pliku 
     * @param ivImage imageview w ktorym ma byc wyswietlana fotka
     */
    public void download(final String szPath,ImageView ivImage)
    {
        final WeakReference<ImageView> wrImage = new WeakReference<ImageView>(ivImage);
        
        final Handler haImage = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {               
                if(wrImage.get() == null)
                {
                    Log.d("@@@","-------------------------------------");
                    Log.d("@@@","wooops is nullll!!!!!!!!!!");
                    Log.d("@@@","-------------------------------------"); 
                    
                    return;
                }          
                
                wrImage.get().setImageBitmap((Bitmap)msg.obj); 
            }
        };
        
        final Thread thImage = new Thread()
        {
            @Override
            public void run()
            {
                Message meMsg   = new Message();
                Bitmap bmBitmap = null;
                
                if(htCache.containsKey(szPath))
                {
                    meMsg.obj = htCache.get(szPath);
                    haImage.sendMessage(meMsg);
                    return;
                }
                
                
                if(isUrl(szPath))
                {
                    bmBitmap = loadRemoteBitmap(szPath);
                    
                    Log.d("@@@","---------------------------------------");
                    Log.d("@@@","siorbiemy z netu");
                    Log.d("@@@","---------------------------------------");
                }
                else if(isLocalImage(szPath))
                {
                    bmBitmap = loadLocalBitmap(szPath);
                    
                    Log.d("@@@","---------------------------------------");
                    Log.d("@@@","siorbiemy lokalnie");
                    Log.d("@@@","---------------------------------------");
                }
                else if(isLocalVideo(szPath))
                {
                    bmBitmap = loadLocalVideoBitmap(szPath);
                    
                    Log.d("@@@","---------------------------------------");
                    Log.d("@@@","siorbiemy z video");
                    Log.d("@@@","---------------------------------------");
                }
                
                if(bmBitmap == null)
                {
                    return;
                }

                
                htCache.put(szPath,bmBitmap);
                
                meMsg.obj = bmBitmap;
                haImage.sendMessage(meMsg);
            }    
        };
        
        thImage.start();
    }
    
    
    /**
     * sprawdza czy podanyc ciag znakow jest poprawnym adresem url
     * 
     * @param szUrl adres url zasobu
     * @return czy adres jest prawidlowy
     */
    private boolean isUrl(String szUrl)
    {
        try
        {
            new URL(szUrl);
        } 
        catch (MalformedURLException e)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * sprawdza czy sciezka do pliku to zdjecie
     * 
     * @param szPath sciezka do pliku
     */
    private boolean isLocalImage(String szPath)
    {
        File fiFile = new File(szPath);
        FileInputStream fisInput = null;
        BitmapFactory.Options bfoOption = new BitmapFactory.Options();
        
        try
        {
            fisInput = new FileInputStream(fiFile);
        } 
        catch (FileNotFoundException e)
        {
            return false;
        }
        
        bfoOption.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(fisInput,null, bfoOption);
        
        try
        {
            fisInput.close();
        } 
        catch (IOException e)
        {
        }
        
        return bfoOption.outWidth == -1 || bfoOption.outHeight == -1 ? false : true;
    }
    
    /**
     * sprawdza czy sciezka do pliku to video
     * 
     * @param szPath sciezka do pliku
     */
    private boolean isLocalVideo(String szPath)
    {
        if(szPath == null) return false;
        return ThumbnailUtils.createVideoThumbnail(szPath,MediaStore.Images.Thumbnails.MINI_KIND) == null ? false : true;
    }
    
    /**
     * laduje miniaturke z pliku graficznego umieszczonego w sieci
     * 
     * @param szUrl sciezka do pliku
     * @return zaladowana bitmapa
     */
    private Bitmap loadRemoteBitmap(String szUrl)
    {
        URL urUrl = null;
        InputStream isInput = null;
        Bitmap bmBitmap = null;
        BitmapFactory.Options bfoOption = new BitmapFactory.Options();
        
        try
        {
            urUrl = new URL(szUrl);
            isInput = urUrl.openConnection().getInputStream();
        } 
        catch(MalformedURLException e)
        {
            return null;
        } 
        catch(IOException e)
        {
            return null;
        }
        
       
        bfoOption.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(isInput,null,bfoOption);
       
        try
        {
            isInput.close();
        } 
        catch (IOException e)
        {
        }
        
        try
        {
            isInput = urUrl.openConnection().getInputStream();
        } 
        catch(MalformedURLException e)
        {
            return null;
        } 
        catch(IOException e)
        {
            return null;
        }
        
        
        iCacheSize += bfoOption.outWidth * bfoOption.outHeight;
        
        if(iCacheSize >= iCacheMax)
        {
            htCache.clear();
            iCacheSize = 0;
        }
        
        
        Log.d("@@@","---------------------------------------");
        Log.d("@@@","cache size: " + iCacheSize);
        Log.d("@@@","mem size:   " + iCacheMax);
        Log.d("@@@","items:      " + htCache.size());
        Log.d("@@@","---------------------------------------");
        
        
        bfoOption.inJustDecodeBounds = false;
        bfoOption.inSampleSize = calculateInSampleSize(bfoOption,IMAGE_WIDTH,IMAGE_HEIGHT);
        bmBitmap = BitmapFactory.decodeStream(isInput,null,bfoOption);
        
        try
        {
            isInput.close();
        } 
        catch (IOException e)
        {
        }
        
        return bmBitmap;
    }
    
    /**
     * laduje miniaturke z pliku graficznego umieszczonego w pamieci telefonu
     * 
     * @param szPath sciezka do pliku
     * @return miniaturka bitmapy
     */
    private Bitmap loadLocalBitmap(String szPath)
    {
        File fiFile = new File(szPath);
        FileInputStream fisInput = null;
        Bitmap bmBitmap = null;
        BitmapFactory.Options bfoOption = new BitmapFactory.Options();
        
        try
        {
            fisInput = new FileInputStream(fiFile);
        } 
        catch (FileNotFoundException e)
        {
            return null;
        }
        
        
        bfoOption.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(fisInput,null,bfoOption);
        
        try { fisInput.close(); } catch (IOException e) { }
        
        
        iCacheSize += bfoOption.outWidth * bfoOption.outHeight;
        
        Log.d("@@@","---------------------------------------");
        Log.d("@@@","cache size: " + iCacheSize);
        Log.d("@@@","mem size:   " + iCacheMax);
        Log.d("@@@","items:      " + htCache.size());
        Log.d("@@@","---------------------------------------");
        
        try
        {
            fisInput = new FileInputStream(fiFile);
        } 
        catch (FileNotFoundException e)
        {
            return null;
        }
        
        bfoOption.inJustDecodeBounds = false;
        bfoOption.inSampleSize = calculateInSampleSize(bfoOption,IMAGE_WIDTH,IMAGE_HEIGHT);
        bmBitmap = BitmapFactory.decodeStream(fisInput,null,bfoOption);
        
        try { fisInput.close(); } catch (IOException e) { }
        
        
        return bmBitmap;
    }
   
    /**
     * laduje miniaturke z pliku video umieszczonego w pamieci telefonu
     * 
     * @param szPath
     * @return
     */
    private Bitmap loadLocalVideoBitmap(String szPath)
    {
       Bitmap bmBitmap = null;
       try
       {
           bmBitmap = ThumbnailUtils.createVideoThumbnail(szPath,MediaStore.Images.Thumbnails.MINI_KIND);
       }
       catch(OutOfMemoryError e)
       {
           return null;
       }
       
       if (bmBitmap == null)
        {
            return null;
        }
       
       iCacheSize += bmBitmap.getWidth() * bmBitmap.getHeight();
       
       if(iCacheSize >= iCacheMax)
       {
           htCache.clear();
           iCacheSize = 0;
       }
       
       Log.d("@@@","---------------------------------------");
       Log.d("@@@","cache size: " + iCacheSize);
       Log.d("@@@","mem size:   " + iCacheMax);
       Log.d("@@@","items:      " + htCache.size());
       Log.d("@@@","---------------------------------------");
       
       return bmBitmap;
    
    }
    
    /**
     * oblicza wspolczynnik zmniejszania przy tworzeniu miniaturki
     * 
     * @param options parametry wejsciowe z szerokoscia i wysokoscia zdjecia
     * @param reqWidth docelowa szerokosc
     * @param reqHeight docelowa wysokosc
     * @return wspolczynnik
     */
    private  int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) 
    {
        final int height = options.outHeight;
        final int width  = options.outWidth;
        int inSampleSize = 1;
    
        if(height > reqHeight || width > reqWidth) 
        {
            if (width > height) 
            {
                inSampleSize = (int) Math.ceil((float)height / (float)reqHeight);
            } 
            else 
            {
                inSampleSize = (int) Math.ceil((float)width / (float)reqWidth);
            }
        }
        
        // If there's no memorry left, try to make the image even worse.
        iCacheSize = (height / inSampleSize) * (width / inSampleSize);
        if(iCacheSize > iCacheMax)
        {
            inSampleSize = (int) Math.ceil(iCacheSize/iCacheMax);
        }

        return inSampleSize;
     }
    
    
}

