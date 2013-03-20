package com.android.lifter.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.os.Build;


/**
 * Klient HTTP bazujacy na HttpURLConnect 
 * 
 */

public final class HttpClient 
{	
	private URL hUrl = null;
	private HttpURLConnection hConn = null;
	
	/**
	 * Konstruktor 
	 * 
	 * @param szUrl adres url na ktore ma byc wysylane wywolanie
	 */
	public HttpClient(String szUrl) throws MalformedURLException
	{
		hUrl = new URL(szUrl);
		
		if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) 
		{
			System.setProperty("http.keepAlive", "false");   
		}
	}
	
	/**
	 * finalizer musi zamknac polaczenie bez wzgledu na wszystko
	 */
	@Override
	protected void finalize() throws Throwable 
	{
		try
		{
			hConn.disconnect();
			
			hUrl = null;
			hConn = null;
		}
		finally
		{
			super.finalize();
		}
	}
	
	/**
	 * Wymusza ignrowanie weryfikacji certyfikatow SSL
	 * 
	 */
	public void sslTrustAllHost()
	{
		TrustManager[] tmManager = new TrustManager[] 
		{
			new X509TrustManager()
			{
				@Override
				public void checkClientTrusted(X509Certificate[] chain,String authType) throws CertificateException 
				{
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException 
				{
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() 
				{
					return new X509Certificate[] {};
				}
			}
		};
				
				
        SSLContext scContext = null;
		
        try 
		{
			scContext = SSLContext.getInstance("TLS");
			scContext.init(null,tmManager,new java.security.SecureRandom());
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
        catch (KeyManagementException e) 
        {
        	e.printStackTrace();
		}
        
        HttpsURLConnection.setDefaultSSLSocketFactory(scContext.getSocketFactory());
	}
	
	/**
	 * Realizuje wywolanie get
	 * 
	 */
	public void get() throws IOException
	{	
		if(hUrl.getProtocol().toLowerCase().equals("https"))
		{
			HostnameVerifier hvVerifier = new HostnameVerifier()
            {
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            };
            
			HttpsURLConnection hHttpsConn = (HttpsURLConnection) hUrl.openConnection();
			hHttpsConn.setHostnameVerifier(hvVerifier);
			hConn = (HttpURLConnection) hHttpsConn;
		}
		else
		{
			hConn = (HttpURLConnection) hUrl.openConnection();
		}
		
		hConn.setRequestMethod("GET");
		hConn.setRequestProperty("User-Agent","MobileAppZapstreak-1.0");
	    hConn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	    hConn.setRequestProperty("Accept-Charset", "utf-8");
		hConn.connect();		
	}
	
	public void setHeader(String szKey,String szValue)
	{
		
	}
	
	/**
	 * Usawa czas przekroczenia przy inicjacji polaczenia
	 * 
	 * @param iTimeout wartosc czasu w milisekundach
	 */
	public void setConnectionTimeout(int iTimeout)
	{
		hConn.setConnectTimeout(iTimeout);
	}
	
	/**
	 * Usawa czas przekroczenia przy inicjacji polaczenia
	 * 
	 * @param iTimeout wartosc czasu w milisekundach
	 */
	public void setReadTimeout(int iTimeout)
	{
		hConn.setReadTimeout(iTimeout);
	}
	
	
	/**
	 * Realizuje wywolanie post bez parametrow
	 * 
	 */
	public void post() throws IOException
	{
		post(null);
	}
	
	/**
	 * Realizuje wywolanie metody post z parametrami
	 * 
	 */
	public void post(Hashtable<String,String> htParams) throws IOException
	{
		String szParam = "";
		byte[] byParam = null;
		DataOutputStream hWr = null;
		
		if(htParams != null)
		{			
			for(Map.Entry<String,String> entry : htParams.entrySet())
			{
				szParam += URLEncoder.encode(entry.getKey(), "UTF-8");	
				szParam += "=";
				szParam += URLEncoder.encode(entry.getValue(), "UTF-8");
				szParam += "&";
			}
		}
		
		try
		{
			byParam = szParam.getBytes("UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			byParam = null;
		}
			
	
		
		if(hUrl.getProtocol().toLowerCase().equals("https"))
		{
			HostnameVerifier hvVerifier = new HostnameVerifier()
            {
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            };
		
			HttpsURLConnection hHttpsConn = (HttpsURLConnection) hUrl.openConnection();
			hHttpsConn.setHostnameVerifier(hvVerifier);
			hConn = (HttpURLConnection) hHttpsConn;
		}
		else
		{
			hConn = (HttpURLConnection) hUrl.openConnection();
		}
		
		hConn.setRequestMethod("POST");  
		hConn.setRequestProperty("User-Agent","MobileAppZapstreak-1.0");
	    hConn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	    hConn.setRequestProperty("Accept-Charset","utf-8");
	    hConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		hConn.setRequestProperty("Content-Length", "" + byParam == null ? "0" : "" + byParam.length);
		hConn.setDoInput(true);
		
		if(byParam != null)
		{
			hConn.setDoOutput(true);
			hConn.getOutputStream().write(byParam);
		}
	}
	
	/**
	 * zamyka polaczenie
	 */
	public void close()
	{
		hConn.disconnect();
	}
	
	/**
	 * Pobiera kod odpowiedzi http
	 * 
	 */
	public int getCode()
	{
		try 
		{
			return hConn.getResponseCode();
		} 
		catch (IOException e) 
		{
			return -1;
		}
		catch (NullPointerException e)
		{
		    return -1;
		}
	}
	
	/**
	 * Pobiera typ mime contentu
	 * 
	 */
	public String getMime()
	{
		return hConn.getContentType();
	}
	
	/**
	 * Pobiera dlugosc contentu
	 * 
	 */
	public int getLength()
	{
		return hConn.getContentLength();
	}
	
	/**
	 * Zwraca wsie naglowki http odpowiedzi
	 * 
	 * @return
	 */
	public String getHeaders()
	{
		StringBuilder sb = new StringBuilder();
		

		
		for(int i=1; ;i++)
		{
			if(hConn.getHeaderField(i) == null)
			{
				break;
			}
			
			if(hConn.getHeaderField(i).length() == 0)
			{
				break;
			}
			
			sb.append(hConn.getHeaderFieldKey(i) + ": " + hConn.getHeaderField(i) + "\r\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * Pobiera content podawany przez serwer jako InputStream
	 * 
	 */
	public InputStream getContentAsStream() throws IOException
	{
		InputStream hIs = null;
		
		if(hConn.getHeaderField("Content-Encoding") != null && hConn.getHeaderField("Content-Encoding").equalsIgnoreCase("gzip"))
		{
			hIs = new GZIPInputStream(hConn.getInputStream());
		}
		else
		{
			hIs = hConn.getInputStream();
		}
		
		return hIs;
	}
	
	/**
	 * Pobiera content podawany przez serwer jako tekst
	 * 
	 */
	public String getContentAsString() throws IOException
	{
		InputStream hIs = null;
		BufferedReader rd = null;
		
		String headerField = ""; 
		try
		{
			headerField = hConn.getHeaderField("Content-Encoding");
		} 
		catch (Exception e) 
		{
			headerField = "";		
		}
		
		if(headerField != null && headerField.equalsIgnoreCase("gzip"))
		{
			hIs = new GZIPInputStream(hConn.getInputStream());
		}
		else
		{
			hIs = hConn.getInputStream();
		}
		if(hIs == null) throw new IOException();
		rd = new BufferedReader(new InputStreamReader(hIs),8*1024);
		
		String szLine = new String();
		StringBuffer hBuff = new StringBuffer();
		    
		while((szLine = rd.readLine()) != null) 
		{
			hBuff.append(szLine);
			hBuff.append('\r');
		}
		
		
		rd.close();
		szLine = hBuff.toString();
		hBuff.setLength(0);
		
		return szLine;
	}
} 

