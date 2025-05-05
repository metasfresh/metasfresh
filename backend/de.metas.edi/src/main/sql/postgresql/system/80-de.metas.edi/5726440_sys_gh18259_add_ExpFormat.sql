-- 2024-06-17T07:11:17.956Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,542414,'N',TO_TIMESTAMP('2024-06-17 07:11:17.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',540428,'Y','N','M_InOut_Desadv_V','N','N','N',TO_TIMESTAMP('2024-06-17 07:11:17.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_Desadv_V','*')
;

-- 2024-06-17T07:12:37.680Z
UPDATE EXP_Format SET EntityType='de.metas.esb.edi', Name='EXP_M_InOut_Desadv_V', Value='EXP_M_InOut_Desadv_V',Updated=TO_TIMESTAMP('2024-06-17 07:12:37.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-06-17T07:12:40.204Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588380,0,TO_TIMESTAMP('2024-06-17 07:12:40.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','de.metas.esb.edi',540428,550596,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','N','Mandant',10,'R',TO_TIMESTAMP('2024-06-17 07:12:40.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_Client_ID')
;

-- 2024-06-17T07:12:40.286Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588381,0,TO_TIMESTAMP('2024-06-17 07:12:40.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540428,550597,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','N','Sektion',20,'R',TO_TIMESTAMP('2024-06-17 07:12:40.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_Org_ID')
;

-- 2024-06-17T07:12:40.356Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588395,0,TO_TIMESTAMP('2024-06-17 07:12:40.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standort des Geschäftspartners für die Rechnungsstellung','de.metas.esb.edi',540428,550598,'N','N','Rechnungsstandort',30,'R',TO_TIMESTAMP('2024-06-17 07:12:40.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bill_Location_ID')
;

-- 2024-06-17T07:12:40.422Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588382,0,TO_TIMESTAMP('2024-06-17 07:12:40.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','de.metas.esb.edi',540428,550599,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','N','N','Geschäftspartner',40,'R',TO_TIMESTAMP('2024-06-17 07:12:40.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner_ID')
;

-- 2024-06-17T07:12:40.486Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588383,0,TO_TIMESTAMP('2024-06-17 07:12:40.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','de.metas.esb.edi',540428,550600,'Identifiziert die Adresse des Geschäftspartners','N','N','Standort',50,'R',TO_TIMESTAMP('2024-06-17 07:12:40.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner_Location_ID')
;

-- 2024-06-17T07:12:40.549Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588396,0,TO_TIMESTAMP('2024-06-17 07:12:40.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','de.metas.esb.edi',540428,550601,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','N','N','Währung',60,'R',TO_TIMESTAMP('2024-06-17 07:12:40.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_Currency_ID')
;

-- 2024-06-17T07:12:40.619Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588384,0,TO_TIMESTAMP('2024-06-17 07:12:40.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540428,550602,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','N','Erstellt',70,'E',TO_TIMESTAMP('2024-06-17 07:12:40.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Created')
;

-- 2024-06-17T07:12:40.691Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588385,0,TO_TIMESTAMP('2024-06-17 07:12:40.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540428,550603,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','N','Erstellt durch',80,'R',TO_TIMESTAMP('2024-06-17 07:12:40.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CreatedBy')
;

-- 2024-06-17T07:12:40.771Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588386,0,TO_TIMESTAMP('2024-06-17 07:12:40.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags','de.metas.esb.edi',540428,550604,'Bezeichnet das Datum, an dem die Ware bestellt wurde.','N','N','Auftragsdatum',90,'E',TO_TIMESTAMP('2024-06-17 07:12:40.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DateOrdered')
;

-- 2024-06-17T07:12:40.848Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588407,0,TO_TIMESTAMP('2024-06-17 07:12:40.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird','de.metas.esb.edi',540428,550605,'"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','N','N','Lieferung',100,'E',TO_TIMESTAMP('2024-06-17 07:12:40.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DeliveryViaRule')
;

-- 2024-06-17T07:12:40.946Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588378,0,TO_TIMESTAMP('2024-06-17 07:12:40.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document sequence number of the document','de.metas.esb.edi',540428,550606,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Nr.',110,'E',TO_TIMESTAMP('2024-06-17 07:12:40.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DocumentNo')
;

-- 2024-06-17T07:12:41.027Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588399,0,TO_TIMESTAMP('2024-06-17 07:12:40.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner to ship to','de.metas.esb.edi',540428,550607,'If empty the business partner will be shipped to.','N','N','Lieferempfänger',120,'R',TO_TIMESTAMP('2024-06-17 07:12:40.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DropShip_BPartner_ID')
;

-- 2024-06-17T07:12:41.120Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588400,0,TO_TIMESTAMP('2024-06-17 07:12:41.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner Location for shipping to','de.metas.esb.edi',540428,550608,'N','N','Lieferadresse',130,'R',TO_TIMESTAMP('2024-06-17 07:12:41.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DropShip_Location_ID')
;

-- 2024-06-17T07:12:41.192Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588379,0,TO_TIMESTAMP('2024-06-17 07:12:41.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550609,'N','N','DESADV',140,'R',TO_TIMESTAMP('2024-06-17 07:12:41.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Desadv_ID')
;

-- 2024-06-17T07:12:41.264Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588388,0,TO_TIMESTAMP('2024-06-17 07:12:41.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550610,'N','N','EDI-Sendestatus',150,'E',TO_TIMESTAMP('2024-06-17 07:12:41.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_ExportStatus')
;

-- 2024-06-17T07:12:41.335Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588387,0,TO_TIMESTAMP('2024-06-17 07:12:41.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550611,'N','N','EDI Fehlermeldung',160,'E',TO_TIMESTAMP('2024-06-17 07:12:41.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDIErrorMsg')
;

-- 2024-06-17T07:12:41.406Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588401,0,TO_TIMESTAMP('2024-06-17 07:12:41.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550612,'N','N','Geliefert %',170,'E',TO_TIMESTAMP('2024-06-17 07:12:41.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'FulfillmentPercent')
;

-- 2024-06-17T07:12:41.485Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588402,0,TO_TIMESTAMP('2024-06-17 07:12:41.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550613,'N','N','Geliefert % Minimum',180,'E',TO_TIMESTAMP('2024-06-17 07:12:41.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'FulfillmentPercentMin')
;

-- 2024-06-17T07:12:41.558Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588397,0,TO_TIMESTAMP('2024-06-17 07:12:41.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550614,'N','N','Übergabeadresse',190,'R',TO_TIMESTAMP('2024-06-17 07:12:41.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HandOver_Location_ID')
;

-- 2024-06-17T07:12:41.635Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588403,0,TO_TIMESTAMP('2024-06-17 07:12:41.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550615,'N','N','Übergabe-Partner',200,'R',TO_TIMESTAMP('2024-06-17 07:12:41.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HandOver_Partner_ID')
;

-- 2024-06-17T07:12:41.714Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588408,0,TO_TIMESTAMP('2024-06-17 07:12:41.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',540428,550616,'N','N','Abr. Menge basiert auf',210,'E',TO_TIMESTAMP('2024-06-17 07:12:41.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'InvoicableQtyBasedOn')
;

-- 2024-06-17T07:12:41.790Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588389,0,TO_TIMESTAMP('2024-06-17 07:12:41.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540428,550617,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',220,'E',TO_TIMESTAMP('2024-06-17 07:12:41.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive')
;

-- 2024-06-17T07:12:41.860Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588376,0,TO_TIMESTAMP('2024-06-17 07:12:41.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550618,'N','Y','M_InOut_Desadv_ID',230,'E',TO_TIMESTAMP('2024-06-17 07:12:41.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_Desadv_ID')
;

-- 2024-06-17T07:12:41.928Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588377,0,TO_TIMESTAMP('2024-06-17 07:12:41.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document','de.metas.esb.edi',540428,550619,'The Material Shipment / Receipt ','N','N','Lieferung/Wareneingang',240,'R',TO_TIMESTAMP('2024-06-17 07:12:41.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_ID')
;

-- 2024-06-17T07:12:41.997Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588390,0,TO_TIMESTAMP('2024-06-17 07:12:41.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','de.metas.esb.edi',540428,550620,'"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.','N','N','Bewegungsdatum',250,'E',TO_TIMESTAMP('2024-06-17 07:12:41.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MovementDate')
;

-- 2024-06-17T07:12:42.067Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588391,0,TO_TIMESTAMP('2024-06-17 07:12:41.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden','de.metas.esb.edi',540428,550621,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','N','N','Referenz',260,'E',TO_TIMESTAMP('2024-06-17 07:12:41.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'POReference')
;

-- 2024-06-17T07:12:42.149Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588392,0,TO_TIMESTAMP('2024-06-17 07:12:42.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','de.metas.esb.edi',540428,550622,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','N','Y','Verarbeitet',270,'E',TO_TIMESTAMP('2024-06-17 07:12:42.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Processed')
;

-- 2024-06-17T07:12:42.224Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588398,0,TO_TIMESTAMP('2024-06-17 07:12:42.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550623,'N','Y','Process Now',280,'E',TO_TIMESTAMP('2024-06-17 07:12:42.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Processing')
;

-- 2024-06-17T07:12:42.301Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588404,0,TO_TIMESTAMP('2024-06-17 07:12:42.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550624,'N','N','SumDeliveredInStockingUOM',290,'E',TO_TIMESTAMP('2024-06-17 07:12:42.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SumDeliveredInStockingUOM')
;

-- 2024-06-17T07:12:42.370Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588405,0,TO_TIMESTAMP('2024-06-17 07:12:42.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,550625,'N','N','SumOrderedInStockingUOM',300,'E',TO_TIMESTAMP('2024-06-17 07:12:42.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SumOrderedInStockingUOM')
;

-- 2024-06-17T07:12:42.441Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588393,0,TO_TIMESTAMP('2024-06-17 07:12:42.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540428,550626,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','N','Aktualisiert',310,'E',TO_TIMESTAMP('2024-06-17 07:12:42.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Updated')
;

-- 2024-06-17T07:12:42.525Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588394,0,TO_TIMESTAMP('2024-06-17 07:12:42.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540428,550627,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','N','Aktualisiert durch',320,'R',TO_TIMESTAMP('2024-06-17 07:12:42.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'UpdatedBy')
;

-- 2024-06-17T07:12:42.601Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588406,0,TO_TIMESTAMP('2024-06-17 07:12:42.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Can be used to flag records and thus make them selectable from the UI via advanced search.','de.metas.esb.edi',540428,550628,'N','N','UserFlag',330,'E',TO_TIMESTAMP('2024-06-17 07:12:42.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'UserFlag')
;

-- 2024-06-17T07:13:57.211Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:13:57.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550598
;

-- 2024-06-17T07:13:58.144Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:13:58.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550599
;

-- 2024-06-17T07:14:02.645Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:02.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550600
;

-- 2024-06-17T07:14:06.747Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:06.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550604
;

-- 2024-06-17T07:14:23.128Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:23.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550605
;

-- 2024-06-17T07:14:24.021Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:24.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550607
;

-- 2024-06-17T07:14:27.242Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:27.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550608
;

-- 2024-06-17T07:14:30.647Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:30.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550609
;

-- 2024-06-17T07:14:32.039Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:32.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550610
;

-- 2024-06-17T07:14:32.975Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:32.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550611
;

-- 2024-06-17T07:14:33.727Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:33.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550612
;

-- 2024-06-17T07:14:42.037Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 07:14:42.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550612
;

-- 2024-06-17T07:14:46.358Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 07:14:46.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550611
;

-- 2024-06-17T07:14:47.163Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:47.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550614
;

-- 2024-06-17T07:14:51.165Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:51.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550615
;

-- 2024-06-17T07:14:52.490Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:52.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550617
;

-- 2024-06-17T07:14:54.703Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:14:54.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550616
;

-- 2024-06-17T07:15:05.323Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 07:15:05.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550617
;

-- 2024-06-17T07:15:06.452Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:15:06.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550620
;

-- 2024-06-17T07:17:36.656Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,542416,'N',TO_TIMESTAMP('2024-06-17 07:17:36.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,'Y','N','M_InOut_DesadvLine_V','N','N','N',TO_TIMESTAMP('2024-06-17 07:17:36.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_DesadvLine_V','*')
;

-- 2024-06-17T07:17:39.146Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588551,0,TO_TIMESTAMP('2024-06-17 07:17:39.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','de.metas.esb.edi',540429,550629,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2024-06-17 07:17:39.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_Client_ID')
;

-- 2024-06-17T07:17:39.229Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588552,0,TO_TIMESTAMP('2024-06-17 07:17:39.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540429,550630,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2024-06-17 07:17:39.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_Org_ID')
;

-- 2024-06-17T07:17:39.305Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588553,0,TO_TIMESTAMP('2024-06-17 07:17:39.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550631,'N','N','BPartner Qty Item Capacity',30,'E',TO_TIMESTAMP('2024-06-17 07:17:39.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BPartner_QtyItemCapacity')
;

-- 2024-06-17T07:17:39.412Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588554,0,TO_TIMESTAMP('2024-06-17 07:17:39.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550632,'N','N','BPartner UOM',40,'R',TO_TIMESTAMP('2024-06-17 07:17:39.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_UOM_BPartner_ID')
;

-- 2024-06-17T07:17:39.479Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588555,0,TO_TIMESTAMP('2024-06-17 07:17:39.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','de.metas.esb.edi',540429,550633,'Eine eindeutige (nicht monetäre) Maßeinheit','N','Y','Maßeinheit',50,'R',TO_TIMESTAMP('2024-06-17 07:17:39.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_UOM_ID')
;

-- 2024-06-17T07:17:39.557Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588556,0,TO_TIMESTAMP('2024-06-17 07:17:39.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit in der die betreffende Zeile abgerechnet wird','de.metas.esb.edi',540429,550634,'N','N','Abrechnungseinheit',60,'R',TO_TIMESTAMP('2024-06-17 07:17:39.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_UOM_Invoice_ID')
;

-- 2024-06-17T07:17:39.621Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588557,0,TO_TIMESTAMP('2024-06-17 07:17:39.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540429,550635,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',70,'E',TO_TIMESTAMP('2024-06-17 07:17:39.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Created')
;

-- 2024-06-17T07:17:39.697Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588558,0,TO_TIMESTAMP('2024-06-17 07:17:39.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540429,550636,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',80,'R',TO_TIMESTAMP('2024-06-17 07:17:39.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CreatedBy')
;

-- 2024-06-17T07:17:39.774Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588559,0,TO_TIMESTAMP('2024-06-17 07:17:39.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550637,'N','N','CU-EAN',90,'E',TO_TIMESTAMP('2024-06-17 07:17:39.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EAN_CU')
;

-- 2024-06-17T07:17:39.848Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588560,0,TO_TIMESTAMP('2024-06-17 07:17:39.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550638,'N','N','TU-EAN',100,'E',TO_TIMESTAMP('2024-06-17 07:17:39.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EAN_TU')
;

-- 2024-06-17T07:17:39.916Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588561,0,TO_TIMESTAMP('2024-06-17 07:17:39.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550639,'N','N','EanCom_Invoice_UOM',110,'E',TO_TIMESTAMP('2024-06-17 07:17:39.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EanCom_Invoice_UOM')
;

-- 2024-06-17T07:17:39.990Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588562,0,TO_TIMESTAMP('2024-06-17 07:17:39.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550640,'N','Y','DESADV',120,'R',TO_TIMESTAMP('2024-06-17 07:17:39.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Desadv_ID')
;

-- 2024-06-17T07:17:40.053Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588564,0,TO_TIMESTAMP('2024-06-17 07:17:39.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550641,'N','N','ExternalSeqNo',130,'E',TO_TIMESTAMP('2024-06-17 07:17:39.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ExternalSeqNo')
;

-- 2024-06-17T07:17:40.126Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588565,0,TO_TIMESTAMP('2024-06-17 07:17:40.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550642,'N','N','GTIN',140,'E',TO_TIMESTAMP('2024-06-17 07:17:40.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GTIN')
;

-- 2024-06-17T07:17:40.200Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588566,0,TO_TIMESTAMP('2024-06-17 07:17:40.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',540429,550643,'N','Y','Abr. Menge basiert auf',150,'E',TO_TIMESTAMP('2024-06-17 07:17:40.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'InvoicableQtyBasedOn')
;

-- 2024-06-17T07:17:40.267Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588567,0,TO_TIMESTAMP('2024-06-17 07:17:40.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540429,550644,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',160,'E',TO_TIMESTAMP('2024-06-17 07:17:40.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive')
;

-- 2024-06-17T07:17:40.345Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588568,0,TO_TIMESTAMP('2024-06-17 07:17:40.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.','de.metas.esb.edi',540429,550645,'N','Y','Spätere Nachlieferung',170,'E',TO_TIMESTAMP('2024-06-17 07:17:40.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsSubsequentDeliveryPlanned')
;

-- 2024-06-17T07:17:40.425Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588569,0,TO_TIMESTAMP('2024-06-17 07:17:40.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einzelne Zeile in dem Dokument','de.metas.esb.edi',540429,550646,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','Zeile Nr.',180,'E',TO_TIMESTAMP('2024-06-17 07:17:40.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Line')
;

-- 2024-06-17T07:17:40.500Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588587,0,TO_TIMESTAMP('2024-06-17 07:17:40.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550647,'N','N','M_InOut_Desadv_ID',190,'E',TO_TIMESTAMP('2024-06-17 07:17:40.427000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_Desadv_ID')
;

-- 2024-06-17T07:17:40.581Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588563,0,TO_TIMESTAMP('2024-06-17 07:17:40.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550648,'N','Y','M_InOut_DesadvLine_V',200,'E',TO_TIMESTAMP('2024-06-17 07:17:40.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_DesadvLine_V_ID')
;

-- 2024-06-17T07:17:40.648Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588570,0,TO_TIMESTAMP('2024-06-17 07:17:40.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','de.metas.esb.edi',540429,550649,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','N','Y','Produkt',210,'R',TO_TIMESTAMP('2024-06-17 07:17:40.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_Product_ID')
;

-- 2024-06-17T07:17:40.715Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588571,0,TO_TIMESTAMP('2024-06-17 07:17:40.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550650,'N','N','Auftragszeile',220,'E',TO_TIMESTAMP('2024-06-17 07:17:40.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'OrderLine')
;

-- 2024-06-17T07:17:40.779Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588572,0,TO_TIMESTAMP('2024-06-17 07:17:40.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550651,'N','N','Auftragsreferenz',230,'E',TO_TIMESTAMP('2024-06-17 07:17:40.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'OrderPOReference')
;

-- 2024-06-17T07:17:40.848Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588573,0,TO_TIMESTAMP('2024-06-17 07:17:40.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Effektiver Preis','de.metas.esb.edi',540429,550652,'Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','N','N','Einzelpreis',240,'E',TO_TIMESTAMP('2024-06-17 07:17:40.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PriceActual')
;

-- 2024-06-17T07:17:40.938Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588574,0,TO_TIMESTAMP('2024-06-17 07:17:40.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produktbeschreibung','de.metas.esb.edi',540429,550653,'N','N','Produktbeschreibung',250,'E',TO_TIMESTAMP('2024-06-17 07:17:40.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProductDescription')
;

-- 2024-06-17T07:17:41.021Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588575,0,TO_TIMESTAMP('2024-06-17 07:17:40.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550654,'N','N','Produktnummer',260,'E',TO_TIMESTAMP('2024-06-17 07:17:40.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProductNo')
;

-- 2024-06-17T07:17:41.105Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588576,0,TO_TIMESTAMP('2024-06-17 07:17:41.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550655,'N','N','Liefermenge (Abrechnungseinheit)',270,'E',TO_TIMESTAMP('2024-06-17 07:17:41.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyDeliveredInInvoiceUOM')
;

-- 2024-06-17T07:17:41.179Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588577,0,TO_TIMESTAMP('2024-06-17 07:17:41.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550656,'N','N','Geliefert (Lagereinheit)',280,'E',TO_TIMESTAMP('2024-06-17 07:17:41.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyDeliveredInStockingUOM')
;

-- 2024-06-17T07:17:41.269Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588578,0,TO_TIMESTAMP('2024-06-17 07:17:41.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)','de.metas.esb.edi',540429,550657,'N','N','Liefermenge',290,'E',TO_TIMESTAMP('2024-06-17 07:17:41.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyDeliveredInUOM')
;

-- 2024-06-17T07:17:41.352Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588579,0,TO_TIMESTAMP('2024-06-17 07:17:41.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','de.metas.esb.edi',540429,550658,'Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','N','N','Menge',300,'E',TO_TIMESTAMP('2024-06-17 07:17:41.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyEntered')
;

-- 2024-06-17T07:17:41.426Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588580,0,TO_TIMESTAMP('2024-06-17 07:17:41.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550659,'N','N','Qty Entered In BPartner UOM',310,'E',TO_TIMESTAMP('2024-06-17 07:17:41.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyEnteredInBPartnerUOM')
;

-- 2024-06-17T07:17:41.501Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588581,0,TO_TIMESTAMP('2024-06-17 07:17:41.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes','de.metas.esb.edi',540429,550660,'N','N','Verpackungskapazität',320,'E',TO_TIMESTAMP('2024-06-17 07:17:41.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyItemCapacity')
;

-- 2024-06-17T07:17:41.575Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588582,0,TO_TIMESTAMP('2024-06-17 07:17:41.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt','de.metas.esb.edi',540429,550661,'Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','N','Y','Bestellt/ Beauftragt',330,'E',TO_TIMESTAMP('2024-06-17 07:17:41.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyOrdered')
;

-- 2024-06-17T07:17:41.654Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588583,0,TO_TIMESTAMP('2024-06-17 07:17:41.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550662,'N','N','CU-UPC',340,'E',TO_TIMESTAMP('2024-06-17 07:17:41.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'UPC_CU')
;

-- 2024-06-17T07:17:41.724Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588584,0,TO_TIMESTAMP('2024-06-17 07:17:41.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550663,'N','N','TU-UPC',350,'E',TO_TIMESTAMP('2024-06-17 07:17:41.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'UPC_TU')
;

-- 2024-06-17T07:17:41.798Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588585,0,TO_TIMESTAMP('2024-06-17 07:17:41.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540429,550664,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',360,'E',TO_TIMESTAMP('2024-06-17 07:17:41.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Updated')
;

-- 2024-06-17T07:17:41.866Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588586,0,TO_TIMESTAMP('2024-06-17 07:17:41.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540429,550665,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',370,'R',TO_TIMESTAMP('2024-06-17 07:17:41.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'UpdatedBy')
;

-- 2024-06-17T07:18:09.601Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:09.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550631
;

-- 2024-06-17T07:18:12.788Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:12.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550632
;

-- 2024-06-17T07:18:17.213Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:17.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550633
;

-- 2024-06-17T07:18:18.045Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:18.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550637
;

-- 2024-06-17T07:18:18.955Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:18.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550638
;

-- 2024-06-17T07:18:34.507Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:34.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550639
;

-- 2024-06-17T07:18:36.004Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:36.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550642
;

-- 2024-06-17T07:18:39.542Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:39.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550641
;

-- 2024-06-17T07:18:42.664Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:42.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550643
;

-- 2024-06-17T07:18:48.304Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:48.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550645
;

-- 2024-06-17T07:18:54.120Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:18:54.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550649
;

-- 2024-06-17T07:19:08.069Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:08.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550650
;

-- 2024-06-17T07:19:11.217Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:11.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550651
;

-- 2024-06-17T07:19:12.233Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:12.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550653
;

-- 2024-06-17T07:19:15.467Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:15.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550652
;

-- 2024-06-17T07:19:16.752Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:16.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550655
;

-- 2024-06-17T07:19:34.746Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:34.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550654
;

-- 2024-06-17T07:19:42.399Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:42.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550657
;

-- 2024-06-17T07:19:45.370Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:45.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550658
;

-- 2024-06-17T07:19:46.259Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:46.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550659
;

-- 2024-06-17T07:19:48.601Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:48.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550660
;

-- 2024-06-17T07:19:49.604Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:49.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550662
;

-- 2024-06-17T07:19:58.406Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:19:58.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550663
;

-- 2024-06-17T07:21:36.112Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,542417,'N',TO_TIMESTAMP('2024-06-17 07:21:35.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,'Y','N','M_InOut_DesadvLine_Pack_V','N','N','N',TO_TIMESTAMP('2024-06-17 07:21:35.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_DesadvLine_Pack_V','*')
;

-- 2024-06-17T07:21:38.100Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588588,0,TO_TIMESTAMP('2024-06-17 07:21:38.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','de.metas.esb.edi',540430,550666,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2024-06-17 07:21:38.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_Client_ID')
;

-- 2024-06-17T07:21:38.161Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588589,0,TO_TIMESTAMP('2024-06-17 07:21:38.104000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540430,550667,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2024-06-17 07:21:38.104000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_Org_ID')
;

-- 2024-06-17T07:21:38.246Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588590,0,TO_TIMESTAMP('2024-06-17 07:21:38.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550668,'N','N','Mindesthaltbarkeitsdatum',30,'E',TO_TIMESTAMP('2024-06-17 07:21:38.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BestBeforeDate')
;

-- 2024-06-17T07:21:38.338Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588591,0,TO_TIMESTAMP('2024-06-17 07:21:38.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','de.metas.esb.edi',540430,550669,'Eine eindeutige (nicht monetäre) Maßeinheit','N','Y','Maßeinheit',40,'R',TO_TIMESTAMP('2024-06-17 07:21:38.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_UOM_ID')
;

-- 2024-06-17T07:21:38.431Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588592,0,TO_TIMESTAMP('2024-06-17 07:21:38.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540430,550670,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',50,'E',TO_TIMESTAMP('2024-06-17 07:21:38.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Created')
;

-- 2024-06-17T07:21:38.501Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588593,0,TO_TIMESTAMP('2024-06-17 07:21:38.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540430,550671,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',60,'R',TO_TIMESTAMP('2024-06-17 07:21:38.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CreatedBy')
;

-- 2024-06-17T07:21:38.607Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588594,0,TO_TIMESTAMP('2024-06-17 07:21:38.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550672,'Y','Y','Y','DESADV',70,'R',TO_TIMESTAMP('2024-06-17 07:21:38.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Desadv_ID')
;

-- 2024-06-17T07:21:38.682Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588595,0,TO_TIMESTAMP('2024-06-17 07:21:38.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550673,'Y','Y','Y','DESADV-Position',80,'R',TO_TIMESTAMP('2024-06-17 07:21:38.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_DesadvLine_ID')
;

-- 2024-06-17T07:21:38.770Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588597,0,TO_TIMESTAMP('2024-06-17 07:21:38.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.','de.metas.esb.edi',540430,550674,'N','N','LU Gebinde-GTIN',90,'E',TO_TIMESTAMP('2024-06-17 07:21:38.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GTIN_LU_PackingMaterial')
;

-- 2024-06-17T07:21:38.847Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588598,0,TO_TIMESTAMP('2024-06-17 07:21:38.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.','de.metas.esb.edi',540430,550675,'N','N','TU Gebinde-GTIN',100,'E',TO_TIMESTAMP('2024-06-17 07:21:38.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'GTIN_TU_PackingMaterial')
;

-- 2024-06-17T07:21:38.922Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588599,0,TO_TIMESTAMP('2024-06-17 07:21:38.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550676,'Y','Y','Y','SSCC18',110,'E',TO_TIMESTAMP('2024-06-17 07:21:38.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IPA_SSCC18')
;

-- 2024-06-17T07:21:39Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588600,0,TO_TIMESTAMP('2024-06-17 07:21:38.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540430,550677,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',120,'E',TO_TIMESTAMP('2024-06-17 07:21:38.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive')
;

-- 2024-06-17T07:21:39.080Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588601,0,TO_TIMESTAMP('2024-06-17 07:21:39.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.','de.metas.esb.edi',540430,550678,'N','Y','manuelle SSCC18',130,'E',TO_TIMESTAMP('2024-06-17 07:21:39.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsManual_IPA_SSCC18')
;

-- 2024-06-17T07:21:39.155Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588602,0,TO_TIMESTAMP('2024-06-17 07:21:39.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550679,'N','N','Chargennummer',140,'E',TO_TIMESTAMP('2024-06-17 07:21:39.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'LotNumber')
;

-- 2024-06-17T07:21:39.226Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588603,0,TO_TIMESTAMP('2024-06-17 07:21:39.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550680,'N','N','Handling Unit',150,'R',TO_TIMESTAMP('2024-06-17 07:21:39.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_ID')
;

-- 2024-06-17T07:21:39.289Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588604,0,TO_TIMESTAMP('2024-06-17 07:21:39.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550681,'N','N','LU Verpackungscode',160,'R',TO_TIMESTAMP('2024-06-17 07:21:39.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_PackagingCode_LU_ID')
;

-- 2024-06-17T07:21:39.360Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588605,0,TO_TIMESTAMP('2024-06-17 07:21:39.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550682,'The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.','N','N','M_HU_PackagingCode_LU_Text',170,'E',TO_TIMESTAMP('2024-06-17 07:21:39.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_PackagingCode_LU_Text')
;

-- 2024-06-17T07:21:39.440Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588606,0,TO_TIMESTAMP('2024-06-17 07:21:39.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550683,'N','N','TU Verpackungscode',180,'R',TO_TIMESTAMP('2024-06-17 07:21:39.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_PackagingCode_TU_ID')
;

-- 2024-06-17T07:21:39.530Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588607,0,TO_TIMESTAMP('2024-06-17 07:21:39.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550684,'The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.','N','N','M_HU_PackagingCode_TU_Text',190,'E',TO_TIMESTAMP('2024-06-17 07:21:39.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_PackagingCode_TU_Text')
;

-- 2024-06-17T07:21:39.608Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588617,0,TO_TIMESTAMP('2024-06-17 07:21:39.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550685,'N','N','M_InOut_DesadvLine_V',200,'E',TO_TIMESTAMP('2024-06-17 07:21:39.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_DesadvLine_V_ID')
;

-- 2024-06-17T07:21:39.684Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588608,0,TO_TIMESTAMP('2024-06-17 07:21:39.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document','de.metas.esb.edi',540430,550686,'The Material Shipment / Receipt ','N','N','Lieferung/Wareneingang',210,'R',TO_TIMESTAMP('2024-06-17 07:21:39.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOut_ID')
;

-- 2024-06-17T07:21:39.756Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588609,0,TO_TIMESTAMP('2024-06-17 07:21:39.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position auf Versand- oder Wareneingangsbeleg','de.metas.esb.edi',540430,550687,'"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','N','N','Versand-/Wareneingangsposition',220,'R',TO_TIMESTAMP('2024-06-17 07:21:39.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_InOutLine_ID')
;

-- 2024-06-17T07:21:39.829Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588610,0,TO_TIMESTAMP('2024-06-17 07:21:39.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge eines bewegten Produktes.','de.metas.esb.edi',540430,550688,'Die "Bewegungs-Menge" bezeichnet die Menge einer Ware, die bewegt wurde.','N','Y','Bewegungs-Menge',230,'E',TO_TIMESTAMP('2024-06-17 07:21:39.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MovementQty')
;

-- 2024-06-17T07:21:39.899Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588611,0,TO_TIMESTAMP('2024-06-17 07:21:39.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge der CUs pro Einzelgebinde (normalerweise TU)','de.metas.esb.edi',540430,550689,'N','N','Menge CU/TU',240,'E',TO_TIMESTAMP('2024-06-17 07:21:39.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyCU')
;

-- 2024-06-17T07:21:39.973Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588612,0,TO_TIMESTAMP('2024-06-17 07:21:39.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550690,'N','N','Menge CU/LU',250,'E',TO_TIMESTAMP('2024-06-17 07:21:39.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyCUsPerLU')
;

-- 2024-06-17T07:21:40.043Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588613,0,TO_TIMESTAMP('2024-06-17 07:21:39.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes','de.metas.esb.edi',540430,550691,'N','N','Verpackungskapazität',260,'E',TO_TIMESTAMP('2024-06-17 07:21:39.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyItemCapacity')
;

-- 2024-06-17T07:21:40.125Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588614,0,TO_TIMESTAMP('2024-06-17 07:21:40.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,550692,'N','N','TU Anzahl',270,'E',TO_TIMESTAMP('2024-06-17 07:21:40.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'QtyTU')
;

-- 2024-06-17T07:21:40.190Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588615,0,TO_TIMESTAMP('2024-06-17 07:21:40.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540430,550693,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',280,'E',TO_TIMESTAMP('2024-06-17 07:21:40.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Updated')
;

-- 2024-06-17T07:21:40.263Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588616,0,TO_TIMESTAMP('2024-06-17 07:21:40.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540430,550694,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',290,'R',TO_TIMESTAMP('2024-06-17 07:21:40.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'UpdatedBy')
;

-- 2024-06-17T07:22:27.410Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:22:27.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550668
;

-- 2024-06-17T07:22:31.476Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:22:31.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550669
;

-- 2024-06-17T07:22:32.243Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 07:22:32.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550672
;

-- 2024-06-17T07:22:37.846Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-06-17 07:22:37.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550673
;

-- 2024-06-17T07:22:38.743Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:22:38.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550675
;

-- 2024-06-17T07:22:44.703Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:22:44.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550674
;

-- 2024-06-17T07:22:51.808Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:22:51.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550679
;

-- 2024-06-17T07:23:05.578Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:23:05.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550682
;

-- 2024-06-17T07:23:10.852Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:23:10.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550684
;

-- 2024-06-17T07:23:12.148Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:23:12.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550689
;

-- 2024-06-17T07:23:14.929Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:23:14.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550690
;

-- 2024-06-17T07:24:01.376Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-06-17 07:24:01.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550692
;

-- 2024-06-17T07:25:53.229Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-06-17 07:25:53.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540428,540428,550695,'Y','N','N','EXP_M_InOut_Desadv_V',340,'M',TO_TIMESTAMP('2024-06-17 07:25:53.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXP_M_InOut_Desadv_V')
;

-- 2024-06-17T07:27:01.767Z
UPDATE EXP_Format SET Name='EXP_M_InOut_DesadvLine_V', Value='EXP_M_InOut_DesadvLine_V',Updated=TO_TIMESTAMP('2024-06-17 07:27:01.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540429
;

-- 2024-06-17T07:27:17.468Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540429, Name='EXP_M_InOut_DesadvLine_V', Value='EXP_M_InOut_DesadvLine_V',Updated=TO_TIMESTAMP('2024-06-17 07:27:17.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550695
;

-- 2024-06-17T07:27:52.912Z
UPDATE EXP_Format SET Name='EXP_M_InOut_DesadvLine_Pack_V', Value='EXP_M_InOut_DesadvLine_Pack_V',Updated=TO_TIMESTAMP('2024-06-17 07:27:52.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540430
;

-- 2024-06-17T07:31:36.595Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-06-17 07:31:36.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540430,540429,550696,'Y','N','N','EXP_M_InOut_DesadvLine_Pack_V',380,'M',TO_TIMESTAMP('2024-06-17 07:31:36.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXP_M_InOut_DesadvLine_Pack_V')
;

-- 2024-06-17T07:38:05.222Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540396, Name='Bill_Location_ID',Updated=TO_TIMESTAMP('2024-06-17 07:38:05.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550598
;

-- 2024-06-17T07:39:10.896Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540385, IsMandatory='Y', IsPartUniqueIndex='Y', Name='C_BPartner_ID',Updated=TO_TIMESTAMP('2024-06-17 07:39:10.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550599
;

-- 2024-06-17T07:39:52.613Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540396, IsMandatory='Y', Name='C_BPartner_Location_ID',Updated=TO_TIMESTAMP('2024-06-17 07:39:52.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550600
;

-- 2024-06-17T07:41:55.832Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540389, IsActive='Y', Name='C_Currency_ID',Updated=TO_TIMESTAMP('2024-06-17 07:41:55.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550601
;

-- 2024-06-17T07:42:03.409Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540396, Name='HandOver_Location_ID',Updated=TO_TIMESTAMP('2024-06-17 07:42:03.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550614
;

-- 2024-06-17T07:42:33.491Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540385, Name='DropShip_BPartner_ID',Updated=TO_TIMESTAMP('2024-06-17 07:42:33.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550607
;

-- 2024-06-17T07:42:50.695Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540396, Name='DropShip_Location_ID',Updated=TO_TIMESTAMP('2024-06-17 07:42:50.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550608
;

-- 2024-06-17T07:43:07.583Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540385, Name='HandOver_Partner_ID',Updated=TO_TIMESTAMP('2024-06-17 07:43:07.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550615
;

-- 2024-06-17T07:45:31.318Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540390, Name='C_UOM_ID',Updated=TO_TIMESTAMP('2024-06-17 07:45:31.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550633
;

-- 2024-06-17T07:46:20.194Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540390, Name='C_UOM_BPartner_ID',Updated=TO_TIMESTAMP('2024-06-17 07:46:20.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550632
;

-- 2024-06-17T07:46:48.107Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540386, Name='M_Product_ID',Updated=TO_TIMESTAMP('2024-06-17 07:46:48.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550649
;

-- 2024-06-17T07:52:10.356Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540390, Name='C_UOM_ID',Updated=TO_TIMESTAMP('2024-06-17 07:52:10.355000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550669
;

-- 2024-06-17T07:56:35.703Z
UPDATE EXP_FormatLine SET Name='EDI_Desadv_ID', Type='E',Updated=TO_TIMESTAMP('2024-06-17 07:56:35.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550609
;

-- Column: M_InOut_DesadvLine_V.IsDeliveryClosed
-- Column: M_InOut_DesadvLine_V.IsDeliveryClosed
-- 2024-06-17T08:34:40.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588618,581020,0,20,542416,'IsDeliveryClosed',TO_TIMESTAMP('2024-06-17 08:34:39.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.esb.edi',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Lieferung geschlossen',0,0,TO_TIMESTAMP('2024-06-17 08:34:39.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-06-17T08:34:40.712Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588618 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-17T08:34:40.730Z
/* DDL */  select update_Column_Translation_From_AD_Element(581020) 
;

-- 2024-06-17T08:36:41.393Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588618,0,TO_TIMESTAMP('2024-06-17 08:36:41.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540429,550697,'Y','Y','N','IsDeliveryClosed',390,'E',TO_TIMESTAMP('2024-06-17 08:36:41.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsDeliveryClosed')
;

