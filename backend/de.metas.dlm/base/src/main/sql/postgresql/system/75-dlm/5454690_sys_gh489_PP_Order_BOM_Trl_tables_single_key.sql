--
-- DDL for PP_Order_BOMLine_Trl
--
-- 16.12.2016 15:08
-- URL zum Konzept
ALTER TABLE PP_Order_BOMLine_Trl ADD COLUMN PP_Order_BOMLine_Trl_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_order_bomline_trl_seq')
;

-- 16.12.2016 15:08
-- URL zum Konzept
ALTER TABLE PP_Order_BOMLine_Trl DROP CONSTRAINT IF EXISTS pp_order_bomline_trl_pkey
;

-- 16.12.2016 15:08
-- URL zum Konzept
ALTER TABLE PP_Order_BOMLine_Trl DROP CONSTRAINT IF EXISTS pp_order_bomline_trl_key
;

-- 16.12.2016 15:08
-- URL zum Konzept
ALTER TABLE PP_Order_BOMLine_Trl ADD CONSTRAINT pp_order_bomline_trl_pkey PRIMARY KEY (PP_Order_BOMLine_Trl_ID)
;


--
-- DDL for PP_Order_BOM_Trl
--
-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Order_BOM_Trl ADD COLUMN PP_Order_BOM_Trl_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_order_bom_trl_seq')
;

-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Order_BOM_Trl DROP CONSTRAINT IF EXISTS pp_order_bom_trl_pkey
;

-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Order_BOM_Trl DROP CONSTRAINT IF EXISTS pp_order_bom_trl_key
;

-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Order_BOM_Trl ADD CONSTRAINT pp_order_bom_trl_pkey PRIMARY KEY (PP_Order_BOM_Trl_ID)
;


--
-- DDL for PP_Product_BOMLine_Trl
--
-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Product_BOMLine_Trl ADD COLUMN PP_Product_BOMLine_Trl_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_product_bomline_trl_seq')
;

-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Product_BOMLine_Trl DROP CONSTRAINT IF EXISTS pp_product_bomline_trl_pkey
;

-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Product_BOMLine_Trl DROP CONSTRAINT IF EXISTS pp_product_bomline_trl_key
;

-- 16.12.2016 15:09
-- URL zum Konzept
ALTER TABLE PP_Product_BOMLine_Trl ADD CONSTRAINT pp_product_bomline_trl_pkey PRIMARY KEY (PP_Product_BOMLine_Trl_ID)
;

-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Node_Trl ADD COLUMN PP_Order_Node_Trl_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_order_node_trl_seq')
;

-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Node_Trl DROP CONSTRAINT IF EXISTS pp_order_node_trl_pkey
;

-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Node_Trl DROP CONSTRAINT IF EXISTS pp_order_node_trl_key
;

-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Node_Trl ADD CONSTRAINT pp_order_node_trl_pkey PRIMARY KEY (PP_Order_Node_Trl_ID)
;


-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Workflow_Trl ADD COLUMN PP_Order_Workflow_Trl_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_order_workflow_trl_seq')
;

-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Workflow_Trl DROP CONSTRAINT IF EXISTS pp_order_workflow_trl_pkey
;

-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Workflow_Trl DROP CONSTRAINT IF EXISTS pp_order_workflow_trl_key
;

-- 16.12.2016 16:12
-- URL zum Konzept
ALTER TABLE PP_Order_Workflow_Trl ADD CONSTRAINT pp_order_workflow_trl_pkey PRIMARY KEY (PP_Order_Workflow_Trl_ID)
;



-- 16.12.2016 16:13
-- URL zum Konzept
ALTER TABLE PP_Product_BOM_Trl ADD COLUMN PP_Product_BOM_Trl_ID numeric(10,0) NOT NULL DEFAULT nextval('pp_product_bom_trl_seq')
;

-- 16.12.2016 16:13
-- URL zum Konzept
ALTER TABLE PP_Product_BOM_Trl DROP CONSTRAINT IF EXISTS pp_product_bom_trl_pkey
;

-- 16.12.2016 16:13
-- URL zum Konzept
ALTER TABLE PP_Product_BOM_Trl DROP CONSTRAINT IF EXISTS pp_product_bom_trl_key
;

-- 16.12.2016 16:13
-- URL zum Konzept
ALTER TABLE PP_Product_BOM_Trl ADD CONSTRAINT pp_product_bom_trl_pkey PRIMARY KEY (PP_Product_BOM_Trl_ID)
;


SELECT dba_seq_check_native();

COMMIT;

--
-- DML for PP_Order_BOMLine_Trl
--

-- 16.12.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543259,0,'PP_Order_BOMLine_Trl_ID',TO_TIMESTAMP('2016-12-16 15:08:10','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing Order BOM Line **','Manufacturing Order BOM Line **',TO_TIMESTAMP('2016-12-16 15:08:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543259 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 16.12.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555709,543259,0,13,53195,'N','PP_Order_BOMLine_Trl_ID',TO_TIMESTAMP('2016-12-16 15:08:10','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Manufacturing Order BOM Line **',TO_TIMESTAMP('2016-12-16 15:08:10','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 16.12.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555709 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 16.12.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555709,557476,0,53218,TO_TIMESTAMP('2016-12-16 15:08:11','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','Manufacturing Order BOM Line **',TO_TIMESTAMP('2016-12-16 15:08:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557476 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for PP_Order_BOM_Trl
--
-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543260,0,'PP_Order_BOM_Trl_ID',TO_TIMESTAMP('2016-12-16 15:09:00','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing Order BOM **','Manufacturing Order BOM **',TO_TIMESTAMP('2016-12-16 15:09:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543260 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555712,543260,0,13,53194,'N','PP_Order_BOM_Trl_ID',TO_TIMESTAMP('2016-12-16 15:09:00','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Manufacturing Order BOM **',TO_TIMESTAMP('2016-12-16 15:09:00','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555712 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555712,557477,0,53217,TO_TIMESTAMP('2016-12-16 15:09:01','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','Manufacturing Order BOM **',TO_TIMESTAMP('2016-12-16 15:09:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557477 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--
-- DML for PP_Product_BOMLine_Trl
--
-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543261,0,'PP_Product_BOMLine_Trl_ID',TO_TIMESTAMP('2016-12-16 15:09:17','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','BOM Line **','BOM Line **',TO_TIMESTAMP('2016-12-16 15:09:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543261 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555715,543261,0,13,53193,'N','PP_Product_BOMLine_Trl_ID',TO_TIMESTAMP('2016-12-16 15:09:17','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','BOM Line **',TO_TIMESTAMP('2016-12-16 15:09:17','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555715 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555715,557478,0,53216,TO_TIMESTAMP('2016-12-16 15:09:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','BOM Line **',TO_TIMESTAMP('2016-12-16 15:09:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557478 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;




-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543262,0,'PP_Order_Node_Trl_ID',TO_TIMESTAMP('2016-12-16 16:12:45','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing Order Activity **','Manufacturing Order Activity **',TO_TIMESTAMP('2016-12-16 16:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543262 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555722,543262,0,13,53190,'N','PP_Order_Node_Trl_ID',TO_TIMESTAMP('2016-12-16 16:12:45','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Manufacturing Order Activity **',TO_TIMESTAMP('2016-12-16 16:12:45','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555722 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555722,557479,0,53214,TO_TIMESTAMP('2016-12-16 16:12:46','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','Manufacturing Order Activity **',TO_TIMESTAMP('2016-12-16 16:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557479 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--------------------

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543263,0,'PP_Order_Workflow_Trl_ID',TO_TIMESTAMP('2016-12-16 16:12:54','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing Order Workflow **','Manufacturing Order Workflow **',TO_TIMESTAMP('2016-12-16 16:12:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543263 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555723,543263,0,13,53189,'N','PP_Order_Workflow_Trl_ID',TO_TIMESTAMP('2016-12-16 16:12:54','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Manufacturing Order Workflow **',TO_TIMESTAMP('2016-12-16 16:12:54','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555723 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555723,557480,0,53213,TO_TIMESTAMP('2016-12-16 16:12:54','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','Manufacturing Order Workflow **',TO_TIMESTAMP('2016-12-16 16:12:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 16:12
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557480 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

--------------

-- 16.12.2016 16:13
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543264,0,'PP_Product_BOM_Trl_ID',TO_TIMESTAMP('2016-12-16 16:13:04','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','BOM & Formula **','BOM & Formula **',TO_TIMESTAMP('2016-12-16 16:13:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 16:13
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543264 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 16.12.2016 16:13
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,555724,543264,0,13,53191,'N','PP_Product_BOM_Trl_ID',TO_TIMESTAMP('2016-12-16 16:13:04','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','BOM & Formula **',TO_TIMESTAMP('2016-12-16 16:13:04','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 16.12.2016 16:13
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555724 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 16.12.2016 16:13
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555724,557481,0,53215,TO_TIMESTAMP('2016-12-16 16:13:04','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','Y','N','N','N','N','N','N','N','BOM & Formula **',TO_TIMESTAMP('2016-12-16 16:13:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 16:13
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557481 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

