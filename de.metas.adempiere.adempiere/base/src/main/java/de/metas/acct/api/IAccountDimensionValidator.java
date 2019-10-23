package de.metas.acct.api;

/**
 * {@link AccountDimension} validator
 * 
 * @author tsa
 *
 */
public interface IAccountDimensionValidator
{
	/**
	 * Checks if given account dimension is completely defined and we are able to create an account from it.
	 * 
	 * @param accountDimension
	 */
	void validate(final AccountDimension accountDimension);

	/**
	 * 
	 * @return accounting schema used by this validator
	 */
	AcctSchema getAcctSchema();

	/**
	 * Sets accounting schema elements to be used while validating. If not set, the default accounting schema elements will be used.
	 * 
	 * @param acctSchemaElements
	 */
	void setAcctSchemaElements(final AcctSchemaElementsMap acctSchemaElements);

	/**
	 * 
	 * @return accounting schema elements used by this validator
	 */
	AcctSchemaElementsMap getAcctSchemaElements();
}
