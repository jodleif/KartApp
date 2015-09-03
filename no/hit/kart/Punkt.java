package no.hit.kart;

/**
 * Created by Jo Øivind Gjernes on 03.09.2015.
 * <p>
 * Klasse punkt - skal samsvare til et punkt på kartet
 */
public class Punkt
{
	private int x;

	private int y;

	public Punkt(int x_pos, int y_pos)
	{
		x = x_pos;
		y = y_pos;
	}

	// For å finne avstand mellom to punkter bruker vi vektormatematikk
	public double avstand(Punkt pt2)
	{
		// Bruker getters - mer oversiktlig. Int er ok- får ikke desimaer av å opphøye i 2 (før man tar roten av utrykket).
		int delta_x = pt2.getX() - getX();
		int delta_y = pt2.getY() - getY();
		return Math.sqrt(delta_x * delta_x + delta_y * delta_y);
	}

	// Autogenererte metoder - get,set
	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}
}
