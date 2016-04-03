package edu.campus02.domain;

/**
 * Gemeinsames Interface f�r alle Dom�nen-Entit�ten, die wir mit unserem 
 * DomainObjectManager persistieren k�nnen wollen (und suchen, mergen, ...).  
 */
public interface DomainObject {
	
	/**
	 * Gibt die ID des Dom�nen-Objektes (also den entsprechend gemappten PrimaryKey) zur�ck. 
	 * In unserem Fall behaupten wir, dass dies immer ein String ist. 
	 * @return ID der aktuellen Entit�t.
	 */
	public String getId();

}
