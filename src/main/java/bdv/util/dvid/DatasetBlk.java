package bdv.util.dvid;

import java.io.IOException;
import java.net.MalformedURLException;

import bdv.util.http.HttpRequest;
import net.imglib2.Interval;
import net.imglib2.RandomAccessibleInterval;

public abstract class DatasetBlk< T > extends Dataset
{

	public DatasetBlk( Node node, String name )
	{
		super( node, name );
	}
	
	public abstract void get( 
			RandomAccessibleInterval< T > target,
			int[] offset
			) throws MalformedURLException, IOException;
	
	public abstract void put( 
			RandomAccessibleInterval< T > source,
			int[] offset
			) throws MalformedURLException, IOException;
	
	public String getBlockRequestUrl( Interval image, int[] offset )
	{
		return getRequestString( getBlockRequestString( image, offset ) );
	}
	
	
	public static String getBlockRequestString( Interval image, int[] offset )
	{
		StringBuilder requestString = new StringBuilder( "raw/0_1_2/" )
				.append( image.dimension( 0 ) ).append( "_" )
				.append( image.dimension( 1 ) ).append( "_" )
				.append( image.dimension( 2 ) ).append( "/" )
				.append( offset[ 0 ] ).append( "_" )
				.append( offset[ 1 ] ).append( "_" )
				.append( offset[ 2 ] )
				;
		return requestString.toString();
	}
	
	public void getByteArray(
			Interval interval,
			byte[] result,
			int[] offset ) throws MalformedURLException, IOException
	{
		
		String requestUrl = getRequestString( getBlockRequestString( interval, offset ) );
		HttpRequest.getRequest( requestUrl, result );
	}
			
}