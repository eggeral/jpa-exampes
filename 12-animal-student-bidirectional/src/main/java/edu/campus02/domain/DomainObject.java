package edu.campus02.domain;

/**
 * Gemeinsames Interface für alle Domänen-Entitäten, die wir mit unserem 
 * DomainObjectManager persistieren können wollen (und suchen, mergen, ...).  
 */
public interface DomainObject {
	
	/**
	 * Gibt die ID des Domänen-Objektes (also den entsprechend gemappten PrimaryKey) zurück. 
	 * In unserem Fall behaupten wir, dass dies immer ein String ist. 
	 * @return ID der aktuellen Entität.
	 */
	public String getId();

}
