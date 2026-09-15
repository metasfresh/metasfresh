/*
 * #%L
 * de.metas.edi
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

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> EDI Fehlermeldung
-- Column: C_Invoice.EDIErrorMsg
-- 2026-02-04T17:18:19.357Z
UPDATE AD_Field SET DisplayLogic='(@EDI_ExportStatus/''X''@=''E'' | @EDI_ExportStatus/''X''@=''I'')',Updated=TO_TIMESTAMP('2026-02-04 17:18:19.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551574
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> DESADV
-- Column: M_InOut.EDI_Desadv_ID
-- 2026-02-04T17:22:35.409Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus@=CO | @DocStatus@=CL) & @EDI_Desadv_ID@>0',Updated=TO_TIMESTAMP('2026-02-04 17:22:35.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555713
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> EDI Fehlermeldung
-- Column: M_InOut.EDIErrorMsg
-- 2026-02-04T17:24:39.865Z
UPDATE AD_Field SET DisplayLogic='@EDI_ExportStatus@=''E'' | @EDI_ExportStatus@=''I''', IsActive='N',Updated=TO_TIMESTAMP('2026-02-04 17:24:39.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553215
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-04T17:25:48.292Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus@=CO | @DocStatus@=CL)',Updated=TO_TIMESTAMP('2026-02-04 17:25:48.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553214
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> EDI-Sendestatus
-- Column: C_Invoice.EDI_ExportStatus
-- 2026-02-04T17:27:10.866Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus/''X''@=''CO'' | @DocStatus/''X''@=''CL'' )',Updated=TO_TIMESTAMP('2026-02-04 17:27:10.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551554
;

-- Field: Wareneingang(184,D) -> Wareneingang(296,D) -> DESADV
-- Column: M_InOut.EDI_Desadv_ID
-- 2026-02-04T17:28:07.991Z
UPDATE AD_Field SET DisplayLogic='@DocStatus@=CO | @DocStatus@=CL & @EDI_Desadv_ID@>0',Updated=TO_TIMESTAMP('2026-02-04 17:28:07.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555714
;

-- Field: Wareneingang(184,D) -> Wareneingang(296,D) -> EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-04T17:28:41.380Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus@=CO | @DocStatus@=CL)', IsActive='N',Updated=TO_TIMESTAMP('2026-02-04 17:28:41.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553217
;

-- Field: Wareneingang(184,D) -> Wareneingang(296,D) -> EDI Fehlermeldung
-- Column: M_InOut.EDIErrorMsg
-- 2026-02-04T17:29:24.738Z
UPDATE AD_Field SET DisplayLogic='(@EDI_ExportStatus@=''E'' | @EDI_ExportStatus@=''I'')', IsActive='N',Updated=TO_TIMESTAMP('2026-02-04 17:29:24.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553218
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> EDI-Sendestatus
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- 2026-02-04T17:30:28.793Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2026-02-04 17:30:28.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555162
;

