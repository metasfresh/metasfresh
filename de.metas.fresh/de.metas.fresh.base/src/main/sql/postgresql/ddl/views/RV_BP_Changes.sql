

DROP VIEW IF EXISTS RV_BP_Changes;

CREATE VIEW RV_BP_Changes
AS
SELECT 
x.TableName, x.Record_ID, x.bpValue, x.bpName, x.ColumnName, x.Created, x.CreatedBy,x.Updated, x.UpdatedBy, x.OldValue, x.NewValue
FROM
(
	(
		SELECT 
			t.name as tablename,
			ch.Record_ID,
			bp.value as bpValue,
			bp.name as bpName,
			c.name as ColumnName,
			ch.Created :: Date,
			ch.CreatedBy,
			ch.Updated :: Date,
			ch.UpdatedBy,
			ch.OldValue,
			ch.NewValue
		FROM	
			ad_changelog ch
			JOIN AD_Table t ON ch.AD_Table_ID = t.AD_Table_ID and t.AD_Table_ID = get_Table_ID('C_BPartner')
			JOIN ad_column c ON ch.ad_column_ID = c.ad_column_ID
			JOIN C_BPartner bp ON ch.Record_ID = bp.C_BPartner_ID
	
	)
	UNION
	(
		
		SELECT 
			t.name as tablename,
			ch.Record_ID,
			bp.value as bpValue,
			bp.name as bpName,
			c.name as ColumnName,
			ch.Created :: Date,
			ch.CreatedBy,
			ch.Updated :: Date,
			ch.UpdatedBy,
			ch.OldValue,
			ch.NewValue
		FROM	
			ad_changelog ch
			JOIN AD_Table t ON ch.AD_Table_ID = t.AD_Table_ID and t.AD_Table_ID = get_Table_ID('C_BPartner_Location')
			JOIN ad_column c ON ch.ad_column_ID = c.ad_column_ID
			JOIN C_Bpartner_Location bpl on ch.Record_ID = bpl.C_BPartner_Location_ID
			JOIN C_BPartner bp ON   bpl.C_BPartner_ID = bp.C_BPartner_ID
	)
	UNION
	(
		
		SELECT 
			t.name as tablename,
			ch.Record_ID,
			bp.value as bpValue,
			bp.name as bpName,
			c.name as ColumnName,
			ch.Created :: Date,
			ch.CreatedBy,
			ch.Updated :: Date,
			ch.UpdatedBy,
			ch.OldValue,
			ch.NewValue
		FROM	
			ad_changelog ch
			JOIN AD_Table t ON ch.AD_Table_ID = t.AD_Table_ID and t.AD_Table_ID = get_Table_ID('C_BP_BankAccount')
			JOIN ad_column c ON ch.ad_column_ID = c.ad_column_ID
			JOIN C_BP_BankAccount bpa on ch.Record_ID = bpa.C_BP_BankAccount_ID
			JOIN C_BPartner bp ON   bpa.C_BPartner_ID = bp.C_BPartner_ID
	)
		 
		
		
) x

GROUP BY x.bpValue, x.bpName, x.tablename, x.record_id, x.columnname, x.created, x.createdBy, x.updated, x.updatedBy, x.oldValue, x.NewValue;