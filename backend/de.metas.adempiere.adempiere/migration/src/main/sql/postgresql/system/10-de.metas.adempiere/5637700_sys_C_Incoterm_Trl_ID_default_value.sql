/* Hotfix issue:
app_1       |   SQL:  INSERT INTO C_Incoterms_Trl(AD_Language, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, C_Incoterms_ID, Name)
app_1       |  SELECT 'de_DE', 'N', t.AD_Client_ID, t.AD_Org_ID, now(), -1, now(), -1, 'Y', t.C_Incoterms_ID, t.Name
app_1       |  FROM C_Incoterms t
app_1       |  LEFT JOIN C_Incoterms_Trl trl ON (trl.C_Incoterms_ID = t.C_Incoterms_ID AND trl.AD_Language='de_DE')
app_1       |  WHERE trl.C_Incoterms_ID IS NULL
app_1       |                   at org.adempiere.exceptions.DBException.wrapIfNeeded(DBException.java:69)
app_1       |                   at org.compiere.util.DB.executeUpdate(DB.java:893)
app_1       |                   at org.compiere.util.DB.executeUpdateEx(DB.java:938)
app_1       |                   at org.compiere.util.DB.executeUpdateEx(DB.java:923)

   NOTE: we might consider dropping that column at all, but here i am just hotfixing the issue.
 */

alter table c_incoterms_trl alter column c_incoterms_trl_id set default nextval('c_incoterms_trl_seq');
