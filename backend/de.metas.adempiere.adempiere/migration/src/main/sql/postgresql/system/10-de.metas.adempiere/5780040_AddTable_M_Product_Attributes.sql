CREATE TABLE M_Product_Attributes
(
    M_Product_Attributes_ID NUMERIC(10, 0) NOT NULL,
    M_Product_ID            NUMERIC(10, 0) NOT NULL,
    AD_Client_ID            NUMERIC(10, 0) NOT NULL,
    AD_Org_ID               NUMERIC(10, 0) NOT NULL,
    IsActive                CHAR(1)   DEFAULT 'Y',
    Created                 TIMESTAMP DEFAULT NOW(),
    CreatedBy               NUMERIC(10, 0),
    Updated                 TIMESTAMP DEFAULT NOW(),
    UpdatedBy               NUMERIC(10, 0),

    SliceWeight             VARCHAR(10),
    SliceThickness          VARCHAR(10),
    SlicesPerStack          VARCHAR(10),
    Dimensions_mm           VARCHAR(10),
    GratingType             VARCHAR(25),
    TechnicalFormatInfo     VARCHAR(50),
    ProgramNumber           VARCHAR(50),
    LoafPreparation         VARCHAR(250),
    AttachLoafLabel         CHAR(1)   DEFAULT 'N',
    LabelingRequired        CHAR(1)   DEFAULT 'N',
    SpecialInfo             VARCHAR(250),

    CONSTRAINT M_Product_Attributes_Key PRIMARY KEY (M_Product_Attributes_ID),
    CONSTRAINT M_Product_FK FOREIGN KEY (M_Product_ID) REFERENCES M_Product (M_Product_ID)
)
;



-- Run mode: SWING_CLIENT

-- Table: M_Product_Attributes
-- 2025-12-08T15:43:23.082Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542563,'A','N',TO_TIMESTAMP('2025-12-08 15:43:22.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','Y','Y','N','N','N','N','N',0,'Product Attribute','NP','L','M_Product_Attributes','DTI',TO_TIMESTAMP('2025-12-08 15:43:22.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2025-12-08T15:43:23.116Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542563 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-12-08T15:43:23.228Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556571,TO_TIMESTAMP('2025-12-08 15:43:23.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table M_Product_Attributes',1,'Y','N','Y','Y','M_Product_Attributes',1000000,TO_TIMESTAMP('2025-12-08 15:43:23.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:43:23.238Z
CREATE SEQUENCE M_PRODUCT_ATTRIBUTES_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2025-12-08T15:43:57.060Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584334,0,'M_Product_Attributes_ID',TO_TIMESTAMP('2025-12-08 15:43:56.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Product Attribute','Product Attribute',TO_TIMESTAMP('2025-12-08 15:43:56.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:43:57.072Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584334 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.M_Product_Attributes_ID
-- 2025-12-08T15:43:57.360Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591677,584334,0,13,542563,'M_Product_Attributes_ID',TO_TIMESTAMP('2025-12-08 15:43:56.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Product Attribute',TO_TIMESTAMP('2025-12-08 15:43:56.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:43:57.364Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591677 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:43:57.391Z
/* DDL */  select update_Column_Translation_From_AD_Element(584334)
;

-- Column: M_Product_Attributes.M_Product_ID
-- 2025-12-08T15:43:57.771Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591678,454,0,30,542563,'M_Product_ID',TO_TIMESTAMP('2025-12-08 15:43:57.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','D',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','Y','N','N','N','Y','Produkt',TO_TIMESTAMP('2025-12-08 15:43:57.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:43:57.779Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591678 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:43:57.781Z
/* DDL */  select update_Column_Translation_From_AD_Element(454)
;

-- Column: M_Product_Attributes.AD_Client_ID
-- 2025-12-08T15:43:58.100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591679,102,0,30,542563,'AD_Client_ID',TO_TIMESTAMP('2025-12-08 15:43:58.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','Y','N','N','N','N','Mandant',TO_TIMESTAMP('2025-12-08 15:43:58.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:43:58.107Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591679 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:43:58.174Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: M_Product_Attributes.AD_Org_ID
-- 2025-12-08T15:43:58.474Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591680,113,0,30,542563,'AD_Org_ID',TO_TIMESTAMP('2025-12-08 15:43:58.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','Y','N','N','N','N','Sektion',TO_TIMESTAMP('2025-12-08 15:43:58.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:43:58.481Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591680 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:43:58.546Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: M_Product_Attributes.IsActive
-- 2025-12-08T15:43:58.834Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591681,348,0,20,542563,'IsActive',TO_TIMESTAMP('2025-12-08 15:43:58.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','N','Y','Aktiv',TO_TIMESTAMP('2025-12-08 15:43:58.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:43:58.840Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591681 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:43:58.904Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: M_Product_Attributes.Created
-- 2025-12-08T15:43:59.209Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591682,245,0,16,542563,'Created',TO_TIMESTAMP('2025-12-08 15:43:59.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2025-12-08 15:43:59.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:43:59.215Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591682 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:43:59.282Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: M_Product_Attributes.CreatedBy
-- 2025-12-08T15:43:59.687Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591683,246,0,18,110,542563,'CreatedBy',TO_TIMESTAMP('2025-12-08 15:43:59.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2025-12-08 15:43:59.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:43:59.694Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591683 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:43:59.770Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: M_Product_Attributes.Updated
-- 2025-12-08T15:44:00.074Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591684,607,0,16,542563,'Updated',TO_TIMESTAMP('2025-12-08 15:43:59.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2025-12-08 15:43:59.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:00.081Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591684 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:00.155Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: M_Product_Attributes.UpdatedBy
-- 2025-12-08T15:44:00.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591685,608,0,18,110,542563,'UpdatedBy',TO_TIMESTAMP('2025-12-08 15:44:00.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2025-12-08 15:44:00.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:00.479Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591685 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:00.553Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-12-08T15:44:00.861Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584335,0,'sliceweight',TO_TIMESTAMP('2025-12-08 15:44:00.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','sliceweight','sliceweight',TO_TIMESTAMP('2025-12-08 15:44:00.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:00.868Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584335 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.sliceweight
-- 2025-12-08T15:44:01.131Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591686,584335,0,10,542563,'sliceweight',TO_TIMESTAMP('2025-12-08 15:44:00.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','sliceweight',TO_TIMESTAMP('2025-12-08 15:44:00.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:01.135Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591686 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:01.212Z
/* DDL */  select update_Column_Translation_From_AD_Element(584335)
;

-- 2025-12-08T15:44:01.500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584336,0,'slicethickness',TO_TIMESTAMP('2025-12-08 15:44:01.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','slicethickness','slicethickness',TO_TIMESTAMP('2025-12-08 15:44:01.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:01.505Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584336 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.slicethickness
-- 2025-12-08T15:44:01.770Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591687,584336,0,10,542563,'slicethickness',TO_TIMESTAMP('2025-12-08 15:44:01.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','slicethickness',TO_TIMESTAMP('2025-12-08 15:44:01.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:01.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591687 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:01.848Z
/* DDL */  select update_Column_Translation_From_AD_Element(584336)
;

-- 2025-12-08T15:44:02.151Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584337,0,'slicesperstack',TO_TIMESTAMP('2025-12-08 15:44:02.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','slicesperstack','slicesperstack',TO_TIMESTAMP('2025-12-08 15:44:02.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:02.157Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584337 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.slicesperstack
-- 2025-12-08T15:44:02.440Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591688,584337,0,10,542563,'slicesperstack',TO_TIMESTAMP('2025-12-08 15:44:02.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','slicesperstack',TO_TIMESTAMP('2025-12-08 15:44:02.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:02.444Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591688 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:02.514Z
/* DDL */  select update_Column_Translation_From_AD_Element(584337)
;

-- 2025-12-08T15:44:02.799Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584338,0,'dimensions_mm',TO_TIMESTAMP('2025-12-08 15:44:02.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','dimensions_mm','dimensions_mm',TO_TIMESTAMP('2025-12-08 15:44:02.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:02.805Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584338 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.dimensions_mm
-- 2025-12-08T15:44:03.081Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591689,584338,0,10,542563,'dimensions_mm',TO_TIMESTAMP('2025-12-08 15:44:02.696000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','dimensions_mm',TO_TIMESTAMP('2025-12-08 15:44:02.696000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:03.084Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591689 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:03.159Z
/* DDL */  select update_Column_Translation_From_AD_Element(584338)
;

-- 2025-12-08T15:44:03.450Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584339,0,'gratingtype',TO_TIMESTAMP('2025-12-08 15:44:03.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','gratingtype','gratingtype',TO_TIMESTAMP('2025-12-08 15:44:03.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:03.457Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584339 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.gratingtype
-- 2025-12-08T15:44:03.729Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591690,584339,0,10,542563,'gratingtype',TO_TIMESTAMP('2025-12-08 15:44:03.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',25,'Y','Y','N','N','N','N','N','N','N','N','Y','gratingtype',TO_TIMESTAMP('2025-12-08 15:44:03.339000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:03.731Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591690 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:03.809Z
/* DDL */  select update_Column_Translation_From_AD_Element(584339)
;

-- 2025-12-08T15:44:04.101Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584340,0,'technicalformatinfo',TO_TIMESTAMP('2025-12-08 15:44:03.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','technicalformatinfo','technicalformatinfo',TO_TIMESTAMP('2025-12-08 15:44:03.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:04.108Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584340 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.technicalformatinfo
-- 2025-12-08T15:44:04.369Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591691,584340,0,10,542563,'technicalformatinfo',TO_TIMESTAMP('2025-12-08 15:44:03.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',50,'Y','Y','N','N','N','N','N','N','N','N','Y','technicalformatinfo',TO_TIMESTAMP('2025-12-08 15:44:03.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:04.372Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591691 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:04.446Z
/* DDL */  select update_Column_Translation_From_AD_Element(584340)
;

-- 2025-12-08T15:44:04.887Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584341,0,'programnumber',TO_TIMESTAMP('2025-12-08 15:44:04.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','programnumber','programnumber',TO_TIMESTAMP('2025-12-08 15:44:04.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:04.895Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584341 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.programnumber
-- 2025-12-08T15:44:05.160Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591692,584341,0,10,542563,'programnumber',TO_TIMESTAMP('2025-12-08 15:44:04.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',50,'Y','Y','N','N','N','N','N','N','N','N','Y','programnumber',TO_TIMESTAMP('2025-12-08 15:44:04.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:05.163Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591692 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:05.236Z
/* DDL */  select update_Column_Translation_From_AD_Element(584341)
;

-- 2025-12-08T15:44:05.519Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584342,0,'loafpreparation',TO_TIMESTAMP('2025-12-08 15:44:05.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','loafpreparation','loafpreparation',TO_TIMESTAMP('2025-12-08 15:44:05.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:05.526Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584342 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.loafpreparation
-- 2025-12-08T15:44:05.795Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591693,584342,0,10,542563,'loafpreparation',TO_TIMESTAMP('2025-12-08 15:44:05.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',250,'Y','Y','N','N','N','N','N','N','N','N','Y','loafpreparation',TO_TIMESTAMP('2025-12-08 15:44:05.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:05.798Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:05.879Z
/* DDL */  select update_Column_Translation_From_AD_Element(584342)
;

-- 2025-12-08T15:44:06.170Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584343,0,'attachloaflabel',TO_TIMESTAMP('2025-12-08 15:44:06.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','attachloaflabel','attachloaflabel',TO_TIMESTAMP('2025-12-08 15:44:06.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:06.177Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584343 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.attachloaflabel
-- 2025-12-08T15:44:06.429Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591694,584343,0,20,542563,'attachloaflabel',TO_TIMESTAMP('2025-12-08 15:44:06.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',1,'Y','Y','N','N','N','N','N','N','N','N','Y','attachloaflabel',TO_TIMESTAMP('2025-12-08 15:44:06.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:06.432Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591694 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:06.510Z
/* DDL */  select update_Column_Translation_From_AD_Element(584343)
;

-- 2025-12-08T15:44:06.810Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584344,0,'labelingrequired',TO_TIMESTAMP('2025-12-08 15:44:06.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','labelingrequired','labelingrequired',TO_TIMESTAMP('2025-12-08 15:44:06.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:06.816Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584344 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.labelingrequired
-- 2025-12-08T15:44:07.088Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591695,584344,0,20,542563,'labelingrequired',TO_TIMESTAMP('2025-12-08 15:44:06.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',1,'Y','Y','N','N','N','N','N','N','N','N','Y','labelingrequired',TO_TIMESTAMP('2025-12-08 15:44:06.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:07.091Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591695 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:07.168Z
/* DDL */  select update_Column_Translation_From_AD_Element(584344)
;

-- 2025-12-08T15:44:07.455Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584345,0,'specialinfo',TO_TIMESTAMP('2025-12-08 15:44:07.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','specialinfo','specialinfo',TO_TIMESTAMP('2025-12-08 15:44:07.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-08T15:44:07.461Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584345 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Product_Attributes.specialinfo
-- 2025-12-08T15:44:07.729Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,591696,584345,0,10,542563,'specialinfo',TO_TIMESTAMP('2025-12-08 15:44:07.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',250,'Y','Y','N','N','N','N','N','N','N','N','Y','specialinfo',TO_TIMESTAMP('2025-12-08 15:44:07.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:44:07.732Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591696 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:44:07.808Z
/* DDL */  select update_Column_Translation_From_AD_Element(584345)
;

-- 2025-12-08T15:48:16.564Z
UPDATE AD_Element SET ColumnName='AttachLoafLabel', Name='AttachLoafLabel', PrintName='AttachLoafLabel',Updated=TO_TIMESTAMP('2025-12-08 15:48:16.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584343
;

-- 2025-12-08T15:48:16.571Z
UPDATE AD_Element_Trl trl SET Name='AttachLoafLabel',PrintName='AttachLoafLabel' WHERE AD_Element_ID=584343 AND AD_Language='de_DE'
;

-- 2025-12-08T15:48:16.572Z
UPDATE AD_Column SET ColumnName='AttachLoafLabel' WHERE AD_Element_ID=584343
;

-- 2025-12-08T15:48:16.574Z
UPDATE AD_Process_Para SET ColumnName='AttachLoafLabel' WHERE AD_Element_ID=584343
;

-- 2025-12-08T15:48:16.580Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584343,'de_DE')
;

-- Element: AttachLoafLabel
-- 2025-12-08T15:48:57.798Z
UPDATE AD_Element_Trl SET Description='Muss der Laib mit einer Laib-Etikette versehen werden?', IsTranslated='Y', Name='Laib-Etikette klebe', PrintName='Laib-Etikette klebe',Updated=TO_TIMESTAMP('2025-12-08 15:48:57.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584343 AND AD_Language='de_CH'
;

-- 2025-12-08T15:48:57.802Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:48:57.997Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584343,'de_CH')
;

-- Element: AttachLoafLabel
-- 2025-12-08T15:49:13.985Z
UPDATE AD_Element_Trl SET Description='Muss der Laib mit einer Laib-Etikette versehen werden?', IsTranslated='Y', Name='Laib-Etikette kleben', PrintName='Laib-Etikette kleben',Updated=TO_TIMESTAMP('2025-12-08 15:49:13.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584343 AND AD_Language='de_DE'
;

-- 2025-12-08T15:49:13.986Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:49:14.683Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584343,'de_DE')
;

-- 2025-12-08T15:49:14.684Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584343,'de_DE')
;

-- Element: AttachLoafLabel
-- 2025-12-08T15:50:27.241Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attach Loaf Label', PrintName='Attach Loaf Label',Updated=TO_TIMESTAMP('2025-12-08 15:50:27.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584343 AND AD_Language='en_US'
;

-- 2025-12-08T15:50:27.242Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:50:27.424Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584343,'en_US')
;

-- Element: AttachLoafLabel
-- 2025-12-08T15:51:15.274Z
UPDATE AD_Element_Trl SET Name='Attach Loaf Label', PrintName='Attach Loaf Label',Updated=TO_TIMESTAMP('2025-12-08 15:51:15.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584343 AND AD_Language='fr_CH'
;

-- 2025-12-08T15:51:15.275Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:51:15.460Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584343,'fr_CH')
;

-- 2025-12-08T15:52:01.944Z
UPDATE AD_Element SET ColumnName='SliceWeight', Name='Scheibengewicht', PrintName='Scheibengewicht',Updated=TO_TIMESTAMP('2025-12-08 15:52:01.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584335
;

-- 2025-12-08T15:52:01.950Z
UPDATE AD_Element_Trl trl SET Name='Scheibengewicht',PrintName='Scheibengewicht' WHERE AD_Element_ID=584335 AND AD_Language='de_DE'
;

-- 2025-12-08T15:52:01.951Z
UPDATE AD_Column SET ColumnName='SliceWeight' WHERE AD_Element_ID=584335
;

-- 2025-12-08T15:52:01.953Z
UPDATE AD_Process_Para SET ColumnName='SliceWeight' WHERE AD_Element_ID=584335
;

-- 2025-12-08T15:52:01.956Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584335,'de_DE')
;

-- Element: SliceWeight
-- 2025-12-08T15:52:07.473Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Scheibengewicht', PrintName='Scheibengewicht',Updated=TO_TIMESTAMP('2025-12-08 15:52:07.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584335 AND AD_Language='de_CH'
;

-- 2025-12-08T15:52:07.474Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:52:07.653Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584335,'de_CH')
;

-- Element: SliceWeight
-- 2025-12-08T15:52:10.886Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 15:52:10.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584335 AND AD_Language='de_DE'
;

-- 2025-12-08T15:52:10.888Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584335,'de_DE')
;

-- 2025-12-08T15:52:10.890Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584335,'de_DE')
;

-- Element: SliceWeight
-- 2025-12-08T15:52:20.402Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Slice weight', PrintName='Slice weight',Updated=TO_TIMESTAMP('2025-12-08 15:52:20.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584335 AND AD_Language='en_US'
;

-- 2025-12-08T15:52:20.403Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:52:20.588Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584335,'en_US')
;

-- Element: SliceWeight
-- 2025-12-08T15:52:36.886Z
UPDATE AD_Element_Trl SET Description='Gewicht der Einzelscheibe für Einstellungen Slicer',Updated=TO_TIMESTAMP('2025-12-08 15:52:36.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584335 AND AD_Language='de_CH'
;

-- 2025-12-08T15:52:36.887Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:52:37.068Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584335,'de_CH')
;

-- Element: SliceWeight
-- 2025-12-08T15:52:39.126Z
UPDATE AD_Element_Trl SET Description='Gewicht der Einzelscheibe für Einstellungen Slicer',Updated=TO_TIMESTAMP('2025-12-08 15:52:39.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584335 AND AD_Language='de_DE'
;

-- 2025-12-08T15:52:39.128Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:52:39.453Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584335,'de_DE')
;

-- 2025-12-08T15:52:39.454Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584335,'de_DE')
;

-- 2025-12-08T15:53:13.152Z
UPDATE AD_Element SET ColumnName='SliceThickness', Name='Scheibendicke', PrintName='Scheibendicke',Updated=TO_TIMESTAMP('2025-12-08 15:53:13.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584336
;

-- 2025-12-08T15:53:13.155Z
UPDATE AD_Element_Trl trl SET Name='Scheibendicke',PrintName='Scheibendicke' WHERE AD_Element_ID=584336 AND AD_Language='de_DE'
;

-- 2025-12-08T15:53:13.156Z
UPDATE AD_Column SET ColumnName='SliceThickness' WHERE AD_Element_ID=584336
;

-- 2025-12-08T15:53:13.157Z
UPDATE AD_Process_Para SET ColumnName='SliceThickness' WHERE AD_Element_ID=584336
;

-- 2025-12-08T15:53:13.160Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584336,'de_DE')
;

-- Element: SliceThickness
-- 2025-12-08T15:53:20.441Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Scheibendicke', PrintName='Scheibendicke',Updated=TO_TIMESTAMP('2025-12-08 15:53:20.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584336 AND AD_Language='de_CH'
;

-- 2025-12-08T15:53:20.442Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:53:20.622Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584336,'de_CH')
;

-- Element: SliceThickness
-- 2025-12-08T15:53:23.054Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 15:53:23.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584336 AND AD_Language='de_DE'
;

-- 2025-12-08T15:53:23.056Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584336,'de_DE')
;

-- 2025-12-08T15:53:23.057Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584336,'de_DE')
;

-- Element: SliceThickness
-- 2025-12-08T15:53:29.938Z
UPDATE AD_Element_Trl SET Description='Dicke der Einzelscheibe für Einstellungen Slicer',Updated=TO_TIMESTAMP('2025-12-08 15:53:29.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584336 AND AD_Language='de_DE'
;

-- 2025-12-08T15:53:29.939Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:53:30.256Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584336,'de_DE')
;

-- 2025-12-08T15:53:30.257Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584336,'de_DE')
;

-- Element: SliceThickness
-- 2025-12-08T15:53:41.097Z
UPDATE AD_Element_Trl SET Description='Dicke der Einzelscheibe für Einstellungen Slicer',Updated=TO_TIMESTAMP('2025-12-08 15:53:41.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584336 AND AD_Language='de_CH'
;

-- 2025-12-08T15:53:41.098Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:53:41.277Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584336,'de_CH')
;

-- Element: SliceThickness
-- 2025-12-08T15:54:18.595Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Slice thickness', PrintName='Slice thickness',Updated=TO_TIMESTAMP('2025-12-08 15:54:18.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584336 AND AD_Language='en_US'
;

-- 2025-12-08T15:54:18.596Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:54:18.783Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584336,'en_US')
;

-- 2025-12-08T15:54:47.385Z
UPDATE AD_Element SET ColumnName='SlicesPerStack', Name='Scheiben pro Stapel', PrintName='Scheiben pro Stapel',Updated=TO_TIMESTAMP('2025-12-08 15:54:47.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584337
;

-- 2025-12-08T15:54:47.387Z
UPDATE AD_Element_Trl trl SET Name='Scheiben pro Stapel',PrintName='Scheiben pro Stapel' WHERE AD_Element_ID=584337 AND AD_Language='de_DE'
;

-- 2025-12-08T15:54:47.389Z
UPDATE AD_Column SET ColumnName='SlicesPerStack' WHERE AD_Element_ID=584337
;

-- 2025-12-08T15:54:47.390Z
UPDATE AD_Process_Para SET ColumnName='SlicesPerStack' WHERE AD_Element_ID=584337
;

-- 2025-12-08T15:54:47.393Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584337,'de_DE')
;

-- Element: SlicesPerStack
-- 2025-12-08T15:54:51.738Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Scheiben pro Stapel', PrintName='Scheiben pro Stapel',Updated=TO_TIMESTAMP('2025-12-08 15:54:51.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584337 AND AD_Language='de_CH'
;

-- 2025-12-08T15:54:51.739Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:54:51.921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584337,'de_CH')
;

-- Element: SlicesPerStack
-- 2025-12-08T15:54:53.883Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 15:54:53.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584337 AND AD_Language='de_DE'
;

-- 2025-12-08T15:54:53.885Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584337,'de_DE')
;

-- 2025-12-08T15:54:53.886Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584337,'de_DE')
;

-- Element: SlicesPerStack
-- 2025-12-08T15:55:04.218Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Slices per stack', PrintName='Slices per stack',Updated=TO_TIMESTAMP('2025-12-08 15:55:04.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584337 AND AD_Language='en_US'
;

-- 2025-12-08T15:55:04.219Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:55:04.396Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584337,'en_US')
;

-- Element: SlicesPerStack
-- 2025-12-08T15:55:12.277Z
UPDATE AD_Element_Trl SET Description='Anzahl Scheiben pro Stapel für Einstellungen Slicer / Picker',Updated=TO_TIMESTAMP('2025-12-08 15:55:12.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584337 AND AD_Language='de_CH'
;

-- 2025-12-08T15:55:12.278Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:55:12.456Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584337,'de_CH')
;

-- Element: SlicesPerStack
-- 2025-12-08T15:55:14.203Z
UPDATE AD_Element_Trl SET Description='Anzahl Scheiben pro Stapel für Einstellungen Slicer / Picker',Updated=TO_TIMESTAMP('2025-12-08 15:55:14.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584337 AND AD_Language='de_DE'
;

-- 2025-12-08T15:55:14.204Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:55:14.504Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584337,'de_DE')
;

-- 2025-12-08T15:55:14.506Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584337,'de_DE')
;

-- 2025-12-08T15:55:48.182Z
UPDATE AD_Element SET ColumnName='Dimensions_mm', Name='Abmessungen in mm', PrintName='Abmessungen in mm',Updated=TO_TIMESTAMP('2025-12-08 15:55:48.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584338
;

-- 2025-12-08T15:55:48.184Z
UPDATE AD_Element_Trl trl SET Name='Abmessungen in mm',PrintName='Abmessungen in mm' WHERE AD_Element_ID=584338 AND AD_Language='de_DE'
;

-- 2025-12-08T15:55:48.185Z
UPDATE AD_Column SET ColumnName='Dimensions_mm' WHERE AD_Element_ID=584338
;

-- 2025-12-08T15:55:48.186Z
UPDATE AD_Process_Para SET ColumnName='Dimensions_mm' WHERE AD_Element_ID=584338
;

-- 2025-12-08T15:55:48.189Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584338,'de_DE')
;

-- Element: Dimensions_mm
-- 2025-12-08T15:56:06.350Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abmessungen in mm', PrintName='Abmessungen in mm',Updated=TO_TIMESTAMP('2025-12-08 15:56:06.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584338 AND AD_Language='de_CH'
;

-- 2025-12-08T15:56:06.351Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:56:06.526Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584338,'de_CH')
;

-- Element: Dimensions_mm
-- 2025-12-08T15:56:10.139Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 15:56:10.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584338 AND AD_Language='de_DE'
;

-- 2025-12-08T15:56:10.141Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584338,'de_DE')
;

-- 2025-12-08T15:56:10.142Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584338,'de_DE')
;

-- Element: Dimensions_mm
-- 2025-12-08T15:56:40.580Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dimensions in mm', PrintName='Dimensions in mm',Updated=TO_TIMESTAMP('2025-12-08 15:56:40.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584338 AND AD_Language='en_US'
;

-- 2025-12-08T15:56:40.581Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:56:40.765Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584338,'en_US')
;

-- Element: Dimensions_mm
-- 2025-12-08T15:56:54.288Z
UPDATE AD_Element_Trl SET Description='Abmessung der Scheiben oder Riemenbreite in mm',Updated=TO_TIMESTAMP('2025-12-08 15:56:54.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584338 AND AD_Language='de_DE'
;

-- 2025-12-08T15:56:54.290Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:56:54.615Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584338,'de_DE')
;

-- 2025-12-08T15:56:54.618Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584338,'de_DE')
;

-- Element: Dimensions_mm
-- 2025-12-08T15:57:03.183Z
UPDATE AD_Element_Trl SET Description='Abmessung der Scheiben oder Riemenbreite in mm',Updated=TO_TIMESTAMP('2025-12-08 15:57:03.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584338 AND AD_Language='de_CH'
;

-- 2025-12-08T15:57:03.184Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T15:57:03.369Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584338,'de_CH')
;

-- 2025-12-08T15:57:35.282Z
/* DDL */ SELECT public.db_alter_table('M_Product_Attributes','ALTER TABLE M_Product_Attributes DROP COLUMN IF EXISTS Dimensions_mm')
;

-- Column: M_Product_Attributes.Dimensions_mm
-- 2025-12-08T15:57:35.352Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591689
;

-- 2025-12-08T15:57:35.366Z
DELETE FROM AD_Column WHERE AD_Column_ID=591689
;

-- 2025-12-08T15:58:41.025Z
UPDATE AD_Element SET ColumnName='Dimensions',Updated=TO_TIMESTAMP('2025-12-08 15:58:41.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584338
;

-- 2025-12-08T15:58:41.033Z
UPDATE AD_Column SET ColumnName='Dimensions' WHERE AD_Element_ID=584338
;

-- 2025-12-08T15:58:41.034Z
UPDATE AD_Process_Para SET ColumnName='Dimensions' WHERE AD_Element_ID=584338
;

-- 2025-12-08T15:58:41.037Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584338,'de_DE')
;

-- Column: M_Product_Attributes.Dimensions
-- 2025-12-08T15:59:06.508Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591697,584338,0,10,542563,'XX','Dimensions',TO_TIMESTAMP('2025-12-08 15:59:06.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Abmessung der Scheiben oder Riemenbreite in mm','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abmessungen in mm',0,0,TO_TIMESTAMP('2025-12-08 15:59:06.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-08T15:59:06.512Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591697 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-08T15:59:06.590Z
/* DDL */  select update_Column_Translation_From_AD_Element(584338)
;

-- 2025-12-08T15:59:08.473Z
/* DDL */ SELECT public.db_alter_table('M_Product_Attributes','ALTER TABLE public.M_Product_Attributes ADD COLUMN Dimensions VARCHAR(10)')
;

-- 2025-12-08T15:59:58.038Z
UPDATE AD_Element SET ColumnName='GratingType',Updated=TO_TIMESTAMP('2025-12-08 15:59:58.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584339
;

-- 2025-12-08T15:59:58.047Z
UPDATE AD_Column SET ColumnName='GratingType' WHERE AD_Element_ID=584339
;

-- 2025-12-08T15:59:58.048Z
UPDATE AD_Process_Para SET ColumnName='GratingType' WHERE AD_Element_ID=584339
;

-- 2025-12-08T15:59:58.050Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584339,'de_DE')
;

-- 2025-12-08T16:00:00.904Z
UPDATE AD_Element SET Name='Reibe-Art', PrintName='Reibe-Art',Updated=TO_TIMESTAMP('2025-12-08 16:00:00.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584339
;

-- 2025-12-08T16:00:00.907Z
UPDATE AD_Element_Trl trl SET Name='Reibe-Art',PrintName='Reibe-Art' WHERE AD_Element_ID=584339 AND AD_Language='de_DE'
;

-- 2025-12-08T16:00:00.911Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584339,'de_DE')
;

-- Element: GratingType
-- 2025-12-08T16:00:06.273Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reibe-Art', PrintName='Reibe-Art',Updated=TO_TIMESTAMP('2025-12-08 16:00:06.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584339 AND AD_Language='de_CH'
;

-- 2025-12-08T16:00:06.274Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:00:06.453Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584339,'de_CH')
;

-- Element: GratingType
-- 2025-12-08T16:00:10.610Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 16:00:10.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584339 AND AD_Language='de_DE'
;

-- 2025-12-08T16:00:10.613Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584339,'de_DE')
;

-- 2025-12-08T16:00:10.614Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584339,'de_DE')
;

-- Element: GratingType
-- 2025-12-08T16:00:26.266Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Grating type', PrintName='Grating type',Updated=TO_TIMESTAMP('2025-12-08 16:00:26.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584339 AND AD_Language='en_US'
;

-- 2025-12-08T16:00:26.267Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:00:26.454Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584339,'en_US')
;

-- Element: GratingType
-- 2025-12-08T16:00:34.352Z
UPDATE AD_Element_Trl SET Description='Definition der Reibe-Art (z.B. Staubreibung,…)',Updated=TO_TIMESTAMP('2025-12-08 16:00:34.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584339 AND AD_Language='de_DE'
;

-- 2025-12-08T16:00:34.354Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:00:34.666Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584339,'de_DE')
;

-- 2025-12-08T16:00:34.668Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584339,'de_DE')
;

-- Element: GratingType
-- 2025-12-08T16:00:43.314Z
UPDATE AD_Element_Trl SET Description='Definition der Reibe-Art (z.B. Staubreibung,…)',Updated=TO_TIMESTAMP('2025-12-08 16:00:43.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584339 AND AD_Language='de_CH'
;

-- 2025-12-08T16:00:43.315Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:00:43.498Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584339,'de_CH')
;

-- 2025-12-08T16:01:12.556Z
UPDATE AD_Element SET ColumnName='TechnicalFormatInfo', Name='Techn. Format-Info', PrintName='Techn. Format-Info',Updated=TO_TIMESTAMP('2025-12-08 16:01:12.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584340
;

-- 2025-12-08T16:01:12.559Z
UPDATE AD_Element_Trl trl SET Name='Techn. Format-Info',PrintName='Techn. Format-Info' WHERE AD_Element_ID=584340 AND AD_Language='de_DE'
;

-- 2025-12-08T16:01:12.560Z
UPDATE AD_Column SET ColumnName='TechnicalFormatInfo' WHERE AD_Element_ID=584340
;

-- 2025-12-08T16:01:12.561Z
UPDATE AD_Process_Para SET ColumnName='TechnicalFormatInfo' WHERE AD_Element_ID=584340
;

-- 2025-12-08T16:01:12.565Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584340,'de_DE')
;

-- Element: TechnicalFormatInfo
-- 2025-12-08T16:01:18.822Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Techn. Format-Info', PrintName='Techn. Format-Info',Updated=TO_TIMESTAMP('2025-12-08 16:01:18.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584340 AND AD_Language='de_CH'
;

-- 2025-12-08T16:01:18.823Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:01:19.007Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584340,'de_CH')
;

-- Element: TechnicalFormatInfo
-- 2025-12-08T16:01:25.853Z
UPDATE AD_Element_Trl SET Description='Rohrgrösse, Werkzeugvorgabe, Format,…',Updated=TO_TIMESTAMP('2025-12-08 16:01:25.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584340 AND AD_Language='de_CH'
;

-- 2025-12-08T16:01:25.854Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:01:26.037Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584340,'de_CH')
;

-- Element: TechnicalFormatInfo
-- 2025-12-08T16:01:33.010Z
UPDATE AD_Element_Trl SET Description='Rohrgrösse, Werkzeugvorgabe, Format,…', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 16:01:33.010000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584340 AND AD_Language='de_DE'
;

-- 2025-12-08T16:01:33.011Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:01:33.317Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584340,'de_DE')
;

-- 2025-12-08T16:01:33.318Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584340,'de_DE')
;

-- Element: TechnicalFormatInfo
-- 2025-12-08T16:01:46.110Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Technical format info', PrintName='Technical format info',Updated=TO_TIMESTAMP('2025-12-08 16:01:46.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584340 AND AD_Language='en_US'
;

-- 2025-12-08T16:01:46.111Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:01:46.288Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584340,'en_US')
;

-- 2025-12-08T16:02:16.502Z
UPDATE AD_Element SET ColumnName='ProgramNumber', Description='Welche Programmnummer muss an einer Anlage eingestellt werden?', Name='Programm-Nummer', PrintName='Programm-Nummer',Updated=TO_TIMESTAMP('2025-12-08 16:02:16.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584341
;

-- 2025-12-08T16:02:16.504Z
UPDATE AD_Element_Trl trl SET Description='Welche Programmnummer muss an einer Anlage eingestellt werden?',Name='Programm-Nummer',PrintName='Programm-Nummer' WHERE AD_Element_ID=584341 AND AD_Language='de_DE'
;

-- 2025-12-08T16:02:16.506Z
UPDATE AD_Column SET ColumnName='ProgramNumber' WHERE AD_Element_ID=584341
;

-- 2025-12-08T16:02:16.507Z
UPDATE AD_Process_Para SET ColumnName='ProgramNumber' WHERE AD_Element_ID=584341
;

-- 2025-12-08T16:02:16.511Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584341,'de_DE')
;

-- Element: ProgramNumber
-- 2025-12-08T16:02:24.985Z
UPDATE AD_Element_Trl SET Description='Welche Programmnummer muss an einer Anlage eingestellt werden?', IsTranslated='Y', Name='Programm-Nummer', PrintName='Programm-Nummer',Updated=TO_TIMESTAMP('2025-12-08 16:02:24.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584341 AND AD_Language='de_CH'
;

-- 2025-12-08T16:02:24.987Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:02:25.171Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584341,'de_CH')
;

-- Element: ProgramNumber
-- 2025-12-08T16:02:31.036Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 16:02:31.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584341 AND AD_Language='de_DE'
;

-- 2025-12-08T16:02:31.039Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584341,'de_DE')
;

-- 2025-12-08T16:02:31.040Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584341,'de_DE')
;

-- Element: ProgramNumber
-- 2025-12-08T16:02:39.894Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Program number', PrintName='Program number',Updated=TO_TIMESTAMP('2025-12-08 16:02:39.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584341 AND AD_Language='en_US'
;

-- 2025-12-08T16:02:39.896Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:02:40.077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584341,'en_US')
;

-- 2025-12-08T16:03:23.506Z
UPDATE AD_Element SET ColumnName='LoafPreparation', Description='Vorgaben wie "Putzen", "Entrinden", "Schaben", "Waschen",….', Name='Laib-Vorbereitung', PrintName='Laib-Vorbereitung',Updated=TO_TIMESTAMP('2025-12-08 16:03:23.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584342
;

-- 2025-12-08T16:03:23.508Z
UPDATE AD_Element_Trl trl SET Description='Vorgaben wie "Putzen", "Entrinden", "Schaben", "Waschen",….',Name='Laib-Vorbereitung',PrintName='Laib-Vorbereitung' WHERE AD_Element_ID=584342 AND AD_Language='de_DE'
;

-- 2025-12-08T16:03:23.510Z
UPDATE AD_Column SET ColumnName='LoafPreparation' WHERE AD_Element_ID=584342
;

-- 2025-12-08T16:03:23.511Z
UPDATE AD_Process_Para SET ColumnName='LoafPreparation' WHERE AD_Element_ID=584342
;

-- 2025-12-08T16:03:23.514Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584342,'de_DE')
;

-- Element: LoafPreparation
-- 2025-12-08T16:03:34.013Z
UPDATE AD_Element_Trl SET Description='Vorgaben wie "Putzen", "Entrinden", "Schaben", "Waschen",….', IsTranslated='Y', Name='Laib-Vorbereitung', PrintName='Laib-Vorbereitung',Updated=TO_TIMESTAMP('2025-12-08 16:03:34.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584342 AND AD_Language='de_CH'
;

-- 2025-12-08T16:03:34.015Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:03:34.200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584342,'de_CH')
;

-- Element: LoafPreparation
-- 2025-12-08T16:03:37.831Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 16:03:37.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584342 AND AD_Language='de_DE'
;

-- 2025-12-08T16:03:37.834Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584342,'de_DE')
;

-- 2025-12-08T16:03:37.835Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584342,'de_DE')
;

-- Element: LoafPreparation
-- 2025-12-08T16:03:47.587Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Loaf Preparation', PrintName='Loaf Preparation',Updated=TO_TIMESTAMP('2025-12-08 16:03:47.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584342 AND AD_Language='en_US'
;

-- 2025-12-08T16:03:47.588Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:03:47.783Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584342,'en_US')
;

-- 2025-12-08T16:04:38.440Z
UPDATE AD_Element SET ColumnName='LabelingRequired', Description='Muss das Produkt mit einer Deklarationsetikette ausgezeichnet werden?', Name='Auszeichnung nötig', PrintName='Auszeichnung nötig',Updated=TO_TIMESTAMP('2025-12-08 16:04:38.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584344
;

-- 2025-12-08T16:04:38.442Z
UPDATE AD_Element_Trl trl SET Description='Muss das Produkt mit einer Deklarationsetikette ausgezeichnet werden?',Name='Auszeichnung nötig',PrintName='Auszeichnung nötig' WHERE AD_Element_ID=584344 AND AD_Language='de_DE'
;

-- 2025-12-08T16:04:38.443Z
UPDATE AD_Column SET ColumnName='LabelingRequired' WHERE AD_Element_ID=584344
;

-- 2025-12-08T16:04:38.444Z
UPDATE AD_Process_Para SET ColumnName='LabelingRequired' WHERE AD_Element_ID=584344
;

-- 2025-12-08T16:04:38.447Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584344,'de_DE')
;

-- Element: LabelingRequired
-- 2025-12-08T16:04:51.718Z
UPDATE AD_Element_Trl SET Description='Muss das Produkt mit einer Deklarationsetikette ausgezeichnet werden?', IsTranslated='Y', Name='Auszeichnung nötig', PrintName='Auszeichnung nötig',Updated=TO_TIMESTAMP('2025-12-08 16:04:51.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584344 AND AD_Language='de_CH'
;

-- 2025-12-08T16:04:51.719Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:04:51.897Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584344,'de_CH')
;

-- Element: LabelingRequired
-- 2025-12-08T16:04:54.515Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 16:04:54.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584344 AND AD_Language='de_DE'
;

-- 2025-12-08T16:04:54.523Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584344,'de_DE')
;

-- 2025-12-08T16:04:54.525Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584344,'de_DE')
;

-- Element: LabelingRequired
-- 2025-12-08T16:05:05.053Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Labeling required', PrintName='Labeling required',Updated=TO_TIMESTAMP('2025-12-08 16:05:05.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584344 AND AD_Language='en_US'
;

-- 2025-12-08T16:05:05.054Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:05:05.230Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584344,'en_US')
;

-- 2025-12-08T16:06:00.178Z
UPDATE AD_Element SET ColumnName='SpecialInfo', Description='Allgemeine Zusatzbemerkungen', Name='Spezielle Info', PrintName='Spezielle Info',Updated=TO_TIMESTAMP('2025-12-08 16:06:00.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584345
;

-- 2025-12-08T16:06:00.181Z
UPDATE AD_Element_Trl trl SET Description='Allgemeine Zusatzbemerkungen',Name='Spezielle Info',PrintName='Spezielle Info' WHERE AD_Element_ID=584345 AND AD_Language='de_DE'
;

-- 2025-12-08T16:06:00.182Z
UPDATE AD_Column SET ColumnName='SpecialInfo' WHERE AD_Element_ID=584345
;

-- 2025-12-08T16:06:00.183Z
UPDATE AD_Process_Para SET ColumnName='SpecialInfo' WHERE AD_Element_ID=584345
;

-- 2025-12-08T16:06:00.186Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584345,'de_DE')
;

-- Element: SpecialInfo
-- 2025-12-08T16:06:11.739Z
UPDATE AD_Element_Trl SET Description='Allgemeine Zusatzbemerkungen', IsTranslated='Y', Name='Spezielle Info', PrintName='Spezielle Info',Updated=TO_TIMESTAMP('2025-12-08 16:06:11.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584345 AND AD_Language='de_CH'
;

-- 2025-12-08T16:06:11.740Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:06:11.920Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584345,'de_CH')
;

-- Element: SpecialInfo
-- 2025-12-08T16:06:15.458Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-08 16:06:15.458000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584345 AND AD_Language='de_DE'
;

-- 2025-12-08T16:06:15.460Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584345,'de_DE')
;

-- 2025-12-08T16:06:15.460Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584345,'de_DE')
;

-- Element: SpecialInfo
-- 2025-12-08T16:06:23.705Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Special info', PrintName='Special info',Updated=TO_TIMESTAMP('2025-12-08 16:06:23.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584345 AND AD_Language='en_US'
;

-- 2025-12-08T16:06:23.706Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-08T16:06:23.895Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584345,'en_US')
;


