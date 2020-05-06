/**
 * 
 */
package de.metas.payment.sepa.api;

import de.metas.payment.sepa.wrapper.BBANStructureEntry;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.BBANCodeEntryType;
import de.metas.payment.sepa.wrapper.BBANStructureEntry.EntryCharacterType;

/**
 * @author cg
 *
 */
public interface IBBANStructureEntryBuilder
{
	/**
	 * Creates the {@link BBANStructureEntry}.
	 * 
	 * @return
	 */
	BBANStructureEntry create();

	IBBANStructureEntryBuilder setCodeType(BBANCodeEntryType codeType);

	IBBANStructureEntryBuilder setCharacterType(EntryCharacterType characterType);

	IBBANStructureEntryBuilder setLength(int length);

	IBBANStructureEntryBuilder setSeqNo(String seqNo);

}
