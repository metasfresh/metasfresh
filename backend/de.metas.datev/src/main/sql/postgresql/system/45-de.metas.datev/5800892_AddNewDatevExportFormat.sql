--------- Datev export format ------------


-- DATEV ExportFormat: Buchungsstapel (116 Spalten, SKR03 )

-- 1. Format header

INSERT INTO DATEV_ExportFormat
(AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, Updated, UpdatedBy,
 DATEV_ExportFormat_ID, Name, CSVEncoding, CSVFieldDelimiter, CSVFieldQuote, DecimalSeparator)
VALUES
    (0, 0, TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'Y',
     TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
     540004, 'Buchungsstapel', 'UTF-8', ';', '', ',')
;


-- 2. Columns  (SeqNo 10..1160)
-- DATEV_ExportFormatColumn_ID starts at 540050 
-- For empty values we use column 592477


-- Col  1 – Umsatz (ohne Soll/Haben-Kz)
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,559149,'Umsatz (ohne Soll/Haben-Kz)',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540050,10);

-- Col  2 – Soll/Haben-Kennzeichen
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Soll/Haben-Kennzeichen',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540051,20);

-- Col  3 – WKZ Umsatz
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'WKZ Umsatz',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540052,30);

-- Col  4 – Kurs
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Kurs',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540053,40);

-- Col  5 – Basis-Umsatz
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Basis-Umsatz',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540054,50);

-- Col  6 – WKZ Basis-Umsatz
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'WKZ Basis-Umsatz',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540055,60);

-- Col  7 – Konto
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,559147,'Konto',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540056,70);

-- Col  8 – Gegenkonto (ohne BU-Schlüssel)
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,559148,'Gegenkonto (ohne BU-Schlüssel)',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540057,80);

-- Col  9 – BU-Schlüssel
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'BU-Schlüssel',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540058,90);

-- Col 10 – Belegdatum  (format DDMM)
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,FormatPattern,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Belegdatum','ddMM',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540059,100);

-- Col 11 – Belegfeld 1  (DocumentNo, prefix-stripped)
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,559151,'Belegfeld 1',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540060,110);

-- Col 12 – Belegfeld 2  (POReference)
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,559152,'Belegfeld 2',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540061,120);

-- Col 13 – Skonto
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Skonto',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540062,130);

-- Col 14 – Buchungstext
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,559221,'Buchungstext',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540063,140);

-- Col 15 – Postensperre
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Postensperre',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540064,150);

-- Col 16 – Diverse Adressnummer
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Diverse Adressnummer',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540065,160);

-- Col 17 – Geschäftspartnerbank
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Geschäftspartnerbank',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540066,170);

-- Col 18 – Sachverhalt
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Sachverhalt',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540067,180);

-- Col 19 – Zinssperre
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Zinssperre',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540068,190);

-- Col 20 – Beleglink
INSERT INTO DATEV_ExportFormatColumn
(DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo)
VALUES (540004,0,0,592477,'Beleglink',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540069,200);

-- Cols 21-36 – Beleginfo Art/Inhalt 1-8
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 1',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540070,210);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 1',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540071,220);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 2',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540072,230);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 2',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540073,240);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 3',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540074,250);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 3',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540075,260);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 4',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540076,270);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 4',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540077,280);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 5',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540078,290);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 5',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540079,300);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 6',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540080,310);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 6',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540081,320);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 7',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540082,330);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 7',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540083,340);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Art 8',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540084,350);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beleginfo - Inhalt 8',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540085,360);

-- Col 37 – KOST1 - Kostenstelle
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,559218,'KOST1 - Kostenstelle',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540086,370);

-- Col 38 – KOST2 - Kostenstelle
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'KOST2 - Kostenstelle',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540087,380);

-- Col 39 – Kost-Menge
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Kost-Menge',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540088,390);

-- Col 40 – EU-Land u. UStID
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'EU-Land u. UStID',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540089,400);

-- Col 41 – EU-Steuersatz
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'EU-Steuersatz',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540090,410);

-- Col 42 – Abw. Versteuerungsart
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Abw. Versteuerungsart',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540091,420);

-- Col 43 – Sachverhalt L+L
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Sachverhalt L+L',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540092,430);

-- Col 44 – Funktionsergänzung L+L
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Funktionsergänzung L+L',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540093,440);

-- Col 45 – BU 49 Hauptfunktionstyp
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'BU 49 Hauptfunktionstyp',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540094,450);

-- Col 46 – BU 49 Hauptfunktionsnummer
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'BU 49 Hauptfunktionsnummer',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540095,460);

-- Col 47 – BU 49 Funktionsergänzung
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'BU 49 Funktionsergänzung',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540096,470);

-- Cols 48-87 – Zusatzinformation Art/Inhalt 1-20
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 1',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540097,480);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 1',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540098,490);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 2',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540099,500);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 2',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540100,510);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 3',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540101,520);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 3',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540102,530);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 4',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540103,540);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 4',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540104,550);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 5',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540105,560);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 5',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540106,570);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 6',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540107,580);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 6',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540108,590);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 7',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540109,600);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 7',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540110,610);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 8',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540111,620);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 8',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540112,630);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 9',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540113,640);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 9',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540114,650);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 10',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540115,660);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 10',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540116,670);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 11',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540117,680);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 11',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540118,690);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 12',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540119,700);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 12',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540120,710);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 13',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540121,720);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 13',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540122,730);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 14',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540123,740);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 14',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540124,750);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 15',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540125,760);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 15',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540126,770);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 16',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540127,780);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 16',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540128,790);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 17',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540129,800);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 17',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540130,810);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 18',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540131,820);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 18',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540132,830);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 19',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540133,840);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 19',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540134,850);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation - Art 20',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540135,860);
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zusatzinformation- Inhalt 20',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540136,870);

-- Col 88 – Stück
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Stück',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540137,880);

-- Col 89 – Gewicht
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Gewicht',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540138,890);

-- Col 90 – Zahlweise
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zahlweise',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540139,900);

-- Col 91 – Forderungsart
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Forderungsart',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540140,910);

-- Col 92 – Veranlagungsjahr
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Veranlagungsjahr',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540141,920);

-- Col 93 – Zugeordnete Fälligkeit
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zugeordnete Fälligkeit',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540142,930);

-- Col 94 – Skontotyp
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Skontotyp',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540143,940);

-- Col 95 – Auftragsnummer
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Auftragsnummer',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540144,950);

-- Col 96 – Buchungstyp
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Buchungstyp',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540145,960);

-- Col 97 – Ust-Schlüssel (Anzahlungen)
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Ust-Schlüssel (Anzahlungen)',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540146,970);

-- Col 98 – EU-Land (Anzahlungen)
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'EU-Land (Anzahlungen)',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540147,980);

-- Col 99 – Sachverhalt L+L (Anzahlungen)
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Sachverhalt L+L (Anzahlungen)',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540148,990);

-- Col 100 – EU-Steuersatz (Anzahlungen)
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'EU-Steuersatz (Anzahlungen)',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540149,1000);

-- Col 101 – Erlöskonto (Anzahlungen)
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Erlöskonto (Anzahlungen)',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540150,1010);

-- Col 102 – Herkunft-Kz
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Herkunft-Kz',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540151,1020);

-- Col 103 – Leerfeld
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Leerfeld',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540152,1030);

-- Col 104 – KOST-Datum
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'KOST-Datum',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540153,1040);

-- Col 105 – Mandatsreferenz
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Mandatsreferenz',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540154,1050);

-- Col 106 – Skontosperre
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Skontosperre',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540155,1060);

-- Col 107 – Gesellschaftername
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Gesellschaftername',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540156,1070);

-- Col 108 – Beteiligtennummer
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Beteiligtennummer',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540157,1080);

-- Col 109 – Identifikationsnummer
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Identifikationsnummer',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540158,1090);

-- Col 110 – Zeichnernummer
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Zeichnernummer',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540159,1100);

-- Col 111 – Postensperre bis
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Postensperre bis',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540160,1110);

-- Col 112 – Bezeichnung SoBil-Sachverhalt
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Bezeichnung SoBil-Sachverhalt',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540161,1120);

-- Col 113 – Kennzeichen SoBil-Buchung
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Kennzeichen SoBil-Buchung',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540162,1130);

-- Col 114 – Festschreibung
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Festschreibung',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540163,1140);

-- Col 115 – Leistungsdatum
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Leistungsdatum',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540164,1150);

-- Col 116 – Datum Zuord.Steuerperiode
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540004,0,0,592477,'Datum Zuord.Steuerperiode',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-05-04 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,540165,1160);



-----------



-- 2026-05-05T12:39:52.465Z
UPDATE DATEV_ExportFormatColumn SET AD_Column_ID=592448,Updated=TO_TIMESTAMP('2026-05-05 12:39:52.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540051
;

-- 2026-05-05T12:42:28.469Z
UPDATE DATEV_ExportFormatColumn SET AD_Column_ID=559152,Updated=TO_TIMESTAMP('2026-05-05 12:42:28.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540059
;

-- 2026-05-05T12:42:35.744Z
UPDATE DATEV_ExportFormatColumn SET FormatPattern='dd.MM.yyyy',Updated=TO_TIMESTAMP('2026-05-05 12:42:35.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540059
;

-- 2026-05-05T12:42:57.456Z
UPDATE DATEV_ExportFormatColumn SET AD_Column_ID=589426,Updated=TO_TIMESTAMP('2026-05-05 12:42:57.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540061
;

-- 2026-05-05T12:49:54.399Z
UPDATE DATEV_ExportFormatColumn SET AD_Column_ID=592477,Updated=TO_TIMESTAMP('2026-05-05 12:49:54.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540086
;

-- 2026-05-05T13:37:44.029Z
UPDATE DATEV_ExportFormatColumn SET AD_Column_ID=592478,Updated=TO_TIMESTAMP('2026-05-05 13:37:44.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540063
;

