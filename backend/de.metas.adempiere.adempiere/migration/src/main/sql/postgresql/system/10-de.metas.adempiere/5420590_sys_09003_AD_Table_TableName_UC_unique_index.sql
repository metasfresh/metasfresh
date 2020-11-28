CREATE UNIQUE INDEX AD_Table_TableName_UC ON AD_Table (UPPER(TableName));
CREATE UNIQUE INDEX ad_column_name_UC ON AD_Column (AD_Table_ID, UPPER(ColumnName));
