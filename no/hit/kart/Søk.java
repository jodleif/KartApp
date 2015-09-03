package no.hit.kart;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Jo Øivind Gjernes on 04.09.2015.
 * <p>
 * Klasse der jeg samler metoder som handler om søk
 */
public class Søk
{
	public static Hendelse søk(Punkt søkePunkt, ArrayList<Hendelse> hendelser)
	{
		Hendelse nærmest;
		int antallHendelser = hendelser.size();
		ArrayList<Double> avstander = new ArrayList<Double>();

		// Primitiv "feilsjekking"
		if (antallHendelser == 0)
			return null;
		if (søkePunkt == null)
			return null;

		for (Hendelse h : hendelser) {
			avstander.add(søkePunkt.avstand(h.getPunkt()));
		}

		nærmest = hendelser.get(minIndex(avstander)); // Finner hendelsen som korresponderer til den minste avstanden.

		return nærmest;
	}

	private static int minIndex(ArrayList<Double> liste)
	{
		return liste.indexOf(Collections.min(liste));
	}
}
