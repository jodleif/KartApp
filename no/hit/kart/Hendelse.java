package no.hit.kart;

/**
 * Created by Jo Øivind Gjernes on 03.09.2015.
 * <p>
 * Klasse - Hendelse.
 * Skal samsvare til en linje på datafilen.
 */
public class Hendelse
{
	private Punkt punkt;
	private String hendelsesTekst;
	private Dato dato;
	private Boolean funnetISøk = false; // flagges dersom den er funnet i et søk. Brukes for å markere

	// Konstruktør for klassen hendelse.
	public Hendelse(Dato dato, Punkt punktPåKartet, String beskrivelse)
	{
		punkt = punktPåKartet;
		hendelsesTekst = beskrivelse;
		this.dato = dato;
	}

	// Alternativ konstruktør, Lage hendelse rett fra "innlest fil"
	public Hendelse(String dato, String xPos, String yPos, String beskrivelse)
	{
		try {
			punkt = new Punkt(xPos, yPos);
		} catch (Exception e) {
			// Kunne kastet eksepsjonen videre men velger bare å opprette et punkt i origo isteden.
			System.err.println("[hendelse] Feil under konstruksjon av punkt - sjekk argumenter!");
			punkt = new Punkt(0, 0);
		}
		this.dato = new Dato(dato);
		this.hendelsesTekst = beskrivelse;
	}

	/* Alternativ konstruktør - leser inn en tekstlinje
	*  Format: Dato, x-posisjon, y-posisjon, beskrivelse av hendelse
	* */
	public Hendelse(String tekstLinje) throws IllegalArgumentException
	{
		String[] parametere = tekstLinje.split(";");

		if (parametere.length != 4) throw new IllegalArgumentException("[Hendelse] Ugyldig input");

		dato = new Dato(parametere[0]);

		try {
			punkt = new Punkt(parametere[1], parametere[2]);
		} catch (IllegalArgumentException e) {
			System.err.println("[Hendelse] Ugyldige koordinater for punkt");
			throw new IllegalArgumentException("[Hendelse] Ugyldige koordinater for punkt");
		}

		hendelsesTekst = parametere[3];
	}

	@Override
	public String toString()
	{
		return "Dato: " + dato.toString() + "\nPunkt [x,y]: [" + punkt.getX() + "," + punkt.getY() + "]\nbeskrivelse: " + hendelsesTekst;
	}
	// Autogenerert kode

	public Punkt getPunkt()
	{
		return punkt;
	}

	public void setPunkt(Punkt punkt)
	{
		this.punkt = punkt;
	}

	public String getHendelsesTekst()
	{
		return hendelsesTekst;
	}

	public void setHendelsesTekst(String hendelsesTekst)
	{
		this.hendelsesTekst = hendelsesTekst;
	}

	public Dato getDato()
	{
		return dato;
	}

	public void setDato(Dato dato)
	{
		this.dato = dato;
	}

	public Boolean getFunnetISøk()
	{
		return funnetISøk;
	}

	public void setFunnetISøk(Boolean funnetISøk)
	{
		this.funnetISøk = funnetISøk;
	}
}
