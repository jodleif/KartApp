package no.hit.kart;

/*
Filen er tatt direkte fra fronter - 03.09.2015

Den eneste endringen jeg har gjort er å reformatere etter mitt oppsett.

 * Klasse for å representere datoer.
 *
 * Objekter kan lages på to måter:
 *
 *   Dato d1 = new Dato(2, 12,1999);
 *   Dato d2 = new Dato("2:12:1999");
 *
 * Klassen inneholder også metoder for å sammenligne datoer 
 * og for å konvertere en dato til tekstlig form.
 *
 */
public class Dato
{

	private int dag;
	private int mnd;
	private int år;

	/*
	     * Konstruktør for å lage Dato-objekter fra heltall dag, måned og år.
	     */
	public Dato(int dag, int mnd, int år)
	{
		this.dag = dag;
		this.mnd = mnd;
		this.år = år;
		sjekk();
	}

	/*
	     * Konstruktør for å lage Dato-objekter fra tekstlig representasjon,
	     * på formen "dd.mm.åååå"
	     */
	public Dato(String strDato)
	{
		try {
			String[] dagTab = strDato.split("\\.");
			dag = Integer.parseInt(dagTab[0]);
			mnd = Integer.parseInt(dagTab[1]);
			år = Integer.parseInt(dagTab[2]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Dato " + strDato + " er ikke på lovlig form dd.mm.åååå");
		}
		sjekk();
	}

	/*
	     * Sjekker om to datoer er like.
	     */
	public boolean erLik(Dato d)
	{
		return år == d.år && mnd == d.mnd && dag == d.dag;
	}

	/*
	     * Sjekker om en dato er før en annen.
	     */
	public boolean før(Dato d)
	{
		boolean før = false;

		if (år < d.år) {
			før = true;
		} else if (år == d.år && mnd < d.mnd) {
			før = true;
		} else if (år == d.år && mnd == d.mnd && dag < d.dag) {
			før = true;
		}

		return før;
	}


	/*
	     * Returnerer en tekstlig representasjon av datoen.
	     */
	public String toString()
	{
		return (Integer.toString(dag) + "."
			+ Integer.toString(mnd) + "."
			+ Integer.toString(år));
	}

	/* ------------ HJELPEMETODER ------------------- */
    /*
     *  Sjekk om denne datoen (this) er lovlig.
     */
	private void sjekk() throws IllegalArgumentException
	{
		boolean ok = true;

		if (mnd < 1 || mnd > 12 || dag < 1 || dag > 31) {
			ok = false;
		} else if ((mnd == 4 || mnd == 6 || mnd == 9 || mnd == 11) && dag > 30) {
			ok = false;
		} else if (mnd == 2 && dag > 29) {
			ok = false;
		} else if (mnd == 2 && dag == 29 && !iSkuddår()) {
			ok = false;
		}

		if (!ok) {
			throw new IllegalArgumentException("Ulovlig dato: " + toString());
		}
	}


	/*
	     *  Sjekk om denne datoen (this) ligger i et skuddår.
	     */
	private boolean iSkuddår()
	{
		return ((år % 4 == 0 && år % 100 != 0) || (år % 400 == 0));
	}
}
