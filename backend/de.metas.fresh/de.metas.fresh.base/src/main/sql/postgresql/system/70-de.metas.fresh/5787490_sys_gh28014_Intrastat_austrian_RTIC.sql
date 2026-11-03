/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Run mode: SWING_CLIENT

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-02-09T13:58:02.335Z
UPDATE AD_Process SET SpreadsheetFormat='csv',Updated=TO_TIMESTAMP('2026-02-09 13:58:02.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-02-09T13:58:34.930Z
UPDATE AD_Process SET IsFormatExcelFile='N', IsTranslateExcelHeaders='N',Updated=TO_TIMESTAMP('2026-02-09 13:58:34.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;    

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-02-09T15:34:43.161Z
UPDATE AD_Process SET Description='Erzeugt eine Datei, die in das Reporting Tool Intra Collect hochgeladen werden kann, um Importe und Exporte zu deklarieren.
Die Zolltarifnummer des jeweiligen Produktes wird als CN8-Code ausgeben',Updated=TO_TIMESTAMP('2026-02-09 15:34:43.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;

-- 2026-02-09T15:34:43.164Z
UPDATE AD_Process_Trl trl SET Description='Erzeugt eine Datei, die in das Reporting Tool Intra Collect hochgeladen werden kann, um Importe und Exporte zu deklarieren.
Die Zolltarifnummer des jeweiligen Produktes wird als CN8-Code ausgeben' WHERE AD_Process_ID=585508 AND AD_Language='de_DE'
;

-- Name: INTRASTAT RTIC Datei (AT)
-- Action Type: P
-- Process: Intrastat_Export(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-09T15:34:43.224Z
UPDATE AD_Menu SET Description='Erzeugt eine Datei, die in das Reporting Tool Intra Collect hochgeladen werden kann, um Importe und Exporte zu deklarieren.
Die Zolltarifnummer des jeweiligen Produktes wird als CN8-Code ausgeben', IsActive='Y', Name='INTRASTAT RTIC Datei (AT)',Updated=TO_TIMESTAMP('2026-02-09 15:34:43.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542261
;

-- 2026-02-09T15:34:43.226Z
UPDATE AD_Menu_Trl trl SET Description='Erzeugt eine Datei, die in das Reporting Tool Intra Collect hochgeladen werden kann, um Importe und Exporte zu deklarieren.
Die Zolltarifnummer des jeweiligen Produktes wird als CN8-Code ausgeben' WHERE AD_Menu_ID=542261 AND AD_Language='de_DE'
;

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-02-09T15:37:21.517Z
UPDATE AD_Process SET TechnicalNote='Using tab as field-delimiter and no field-quote. 
This is aiming towards the autrian RTIC-Tool ("Reporting Tool Intra Collect")',Updated=TO_TIMESTAMP('2026-02-09 15:37:21.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;

-- Value: Intrastat_Export
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2026-02-09T15:37:25.586Z
UPDATE AD_Process SET CSVFieldQuote='',Updated=TO_TIMESTAMP('2026-02-09 15:37:25.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585508
;

