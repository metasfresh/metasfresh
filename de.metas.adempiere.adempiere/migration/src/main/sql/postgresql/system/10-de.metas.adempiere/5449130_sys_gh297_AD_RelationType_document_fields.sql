-- Aug 17, 2016 8:06 AM
-- URL zum Konzept
UPDATE AD_Reference SET Name='RelType C_Order -> C_OLCand',Updated=TO_TIMESTAMP('2016-08-17 08:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540618
;

-- Aug 17, 2016 8:06 AM
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540618
;

-- Aug 17, 2016 9:58 AM
-- URL zum Konzept
UPDATE AD_Element SET Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the srouce reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.',Updated=TO_TIMESTAMP('2016-08-17 09:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 9:58 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 9:58 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the srouce reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 9:58 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the srouce reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.', AD_Element_ID=54071 WHERE UPPER(ColumnName)='AD_REFERENCE_SOURCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 9:58 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the srouce reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.' WHERE AD_Element_ID=54071 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 9:58 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the srouce reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54071) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:00 AM
-- URL zum Konzept
UPDATE AD_Element SET Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.',Updated=TO_TIMESTAMP('2016-08-17 10:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:00 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:00 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:00 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.', AD_Element_ID=54071 WHERE UPPER(ColumnName)='AD_REFERENCE_SOURCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:00 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.' WHERE AD_Element_ID=54071 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:00 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Source Reference', Description='For directed relation types, the whereclause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relations source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role the the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullyfill both roles logically, but not in a performant way.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54071) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:08 AM
-- URL zum Konzept
UPDATE AD_Element SET Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.',Updated=TO_TIMESTAMP('2016-08-17 10:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:08 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:08 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:08 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.', AD_Element_ID=54071 WHERE UPPER(ColumnName)='AD_REFERENCE_SOURCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:08 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54071 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:08 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table''s whereclause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54071) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:10 AM
-- URL zum Konzept
UPDATE AD_Element SET Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).',Updated=TO_TIMESTAMP('2016-08-17 10:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54072
;

-- Aug 17, 2016 10:10 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54072
;

-- Aug 17, 2016 10:10 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Reference_Target_ID', Name='Target Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help=NULL WHERE AD_Element_ID=54072
;

-- Aug 17, 2016 10:10 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Target_ID', Name='Target Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help=NULL, AD_Element_ID=54072 WHERE UPPER(ColumnName)='AD_REFERENCE_TARGET_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:10 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Target_ID', Name='Target Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help=NULL WHERE AD_Element_ID=54072 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:10 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Target Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54072) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:12 AM
-- URL zum Konzept
UPDATE AD_Element SET Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.',Updated=TO_TIMESTAMP('2016-08-17 10:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:12 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:12 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:12 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.', AD_Element_ID=54071 WHERE UPPER(ColumnName)='AD_REFERENCE_SOURCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:12 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54071 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:12 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID playes in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54071) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:15 AM
-- URL zum Konzept
UPDATE AD_Element SET Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.',Updated=TO_TIMESTAMP('2016-08-17 10:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:15 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:15 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54071
;

-- Aug 17, 2016 10:15 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.', AD_Element_ID=54071 WHERE UPPER(ColumnName)='AD_REFERENCE_SOURCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:15 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Source_ID', Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54071 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:15 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Source Reference', Description='For directed relation types, the where clause of this reference''s AD_Ref_Table is used to decide if a given record is a possible relation source.', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Source''s AD_Ref_Table''s whereclause is executed as 
"SELECT 1 FROM C_Order WHERE" <AD_Ref_Table.WhereClause> "...AND C_Order_ID=<current order''s C_Order_ID>"

Also note that for undirected AD_Relation_Types, the source reference can also play the role that the AD_Reference_Target_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54071) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:16 AM
-- URL zum Konzept
UPDATE AD_Element SET Description='For directed relation types, the table and where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Target''s AD_Ref_Table''s AD_Table and whereclause is executed as 
"SELECT * FROM <AD_Ref_Table.AD_Table.TableName> WHERE" <AD_Ref_Table.WhereClause>.
For this to work, the WhereClause generally needs some context variable. E.g for order->shipment, there needs to be something like "M_InOut.C_Order_ID=@C_Order_ID@", so that the system can fill in the current order''s ID.

Also note that for undirected AD_Relation_Types, the target reference can also play the role that the AD_Reference_Source_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.',Updated=TO_TIMESTAMP('2016-08-17 10:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54072
;

-- Aug 17, 2016 10:16 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54072
;

-- Aug 17, 2016 10:16 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_Reference_Target_ID', Name='Target Reference', Description='For directed relation types, the table and where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Target''s AD_Ref_Table''s AD_Table and whereclause is executed as 
"SELECT * FROM <AD_Ref_Table.AD_Table.TableName> WHERE" <AD_Ref_Table.WhereClause>.
For this to work, the WhereClause generally needs some context variable. E.g for order->shipment, there needs to be something like "M_InOut.C_Order_ID=@C_Order_ID@", so that the system can fill in the current order''s ID.

Also note that for undirected AD_Relation_Types, the target reference can also play the role that the AD_Reference_Source_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54072
;

-- Aug 17, 2016 10:16 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Target_ID', Name='Target Reference', Description='For directed relation types, the table and where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Target''s AD_Ref_Table''s AD_Table and whereclause is executed as 
"SELECT * FROM <AD_Ref_Table.AD_Table.TableName> WHERE" <AD_Ref_Table.WhereClause>.
For this to work, the WhereClause generally needs some context variable. E.g for order->shipment, there needs to be something like "M_InOut.C_Order_ID=@C_Order_ID@", so that the system can fill in the current order''s ID.

Also note that for undirected AD_Relation_Types, the target reference can also play the role that the AD_Reference_Source_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.', AD_Element_ID=54072 WHERE UPPER(ColumnName)='AD_REFERENCE_TARGET_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:16 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Reference_Target_ID', Name='Target Reference', Description='For directed relation types, the table and where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Target''s AD_Ref_Table''s AD_Table and whereclause is executed as 
"SELECT * FROM <AD_Ref_Table.AD_Table.TableName> WHERE" <AD_Ref_Table.WhereClause>.
For this to work, the WhereClause generally needs some context variable. E.g for order->shipment, there needs to be something like "M_InOut.C_Order_ID=@C_Order_ID@", so that the system can fill in the current order''s ID.

Also note that for undirected AD_Relation_Types, the target reference can also play the role that the AD_Reference_Source_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Element_ID=54072 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:16 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Target Reference', Description='For directed relation types, the table and where clause of this reference''s AD_Ref_Table is used to select the relation partners for a given source-record (e.g. the shipments for a given order).', Help='Example: for "zoom-into" from an order-window, this means that the AD_Reference_Target''s AD_Ref_Table''s AD_Table and whereclause is executed as 
"SELECT * FROM <AD_Ref_Table.AD_Table.TableName> WHERE" <AD_Ref_Table.WhereClause>.
For this to work, the WhereClause generally needs some context variable. E.g for order->shipment, there needs to be something like "M_InOut.C_Order_ID=@C_Order_ID@", so that the system can fill in the current order''s ID.

Also note that for undirected AD_Relation_Types, the target reference can also play the role that the AD_Reference_Source_ID plays in directed types. 
This can cause performance problems as where-clauses can be written to fullfill both roles logically, but in an very unperformant way.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54072) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:19 AM
-- URL zum Konzept
UPDATE AD_Element SET Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it might be better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.',Updated=TO_TIMESTAMP('2016-08-17 10:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54074
;

-- Aug 17, 2016 10:19 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54074
;

-- Aug 17, 2016 10:19 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDirected', Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it might be better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.' WHERE AD_Element_ID=54074
;

-- Aug 17, 2016 10:19 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDirected', Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it might be better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.', AD_Element_ID=54074 WHERE UPPER(ColumnName)='ISDIRECTED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:19 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDirected', Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it might be better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.' WHERE AD_Element_ID=54074 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:19 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it might be better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54074) AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:20 AM
-- URL zum Konzept
UPDATE AD_Element SET Help='Warning: for implicit relation realtion types in particular, it is often better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.',Updated=TO_TIMESTAMP('2016-08-17 10:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54074
;

-- Aug 17, 2016 10:20 AM
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=54074
;

-- Aug 17, 2016 10:20 AM
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDirected', Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it is often better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.' WHERE AD_Element_ID=54074
;

-- Aug 17, 2016 10:20 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDirected', Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it is often better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.', AD_Element_ID=54074 WHERE UPPER(ColumnName)='ISDIRECTED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- Aug 17, 2016 10:20 AM
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDirected', Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it is often better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.' WHERE AD_Element_ID=54074 AND IsCentrallyMaintained='Y'
;

-- Aug 17, 2016 10:20 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Directed', Description='Tells whether one "sees" the other end of the relation from each end or just from the source.', Help='Warning: for implicit relation realtion types in particular, it is often better to have two directed types as opposed to one one-directed.
The reason for that is, that for non-directed types, both the type''s AD_Reference_Source and AD_Reference_Target need to be able to work in the role of the other role.
Those types are othen harder to understand, and those references'' AD_Table_Refs'' WhereClauses are often not performant.' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=54074) AND IsCentrallyMaintained='Y'
;

