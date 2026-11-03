-- gh28121: Add proper descriptions to revenue report processes and parameters
-- Also fix untranslated parameter names (Week→Woche, Report format→Berichtsformat, etc.)

-- ============================================================================
-- Process descriptions (DE base + EN translation)
-- ============================================================================

-- 540558 Umsatzreport
UPDATE ad_process SET description = 'Umsatzreport nach Periode mit Vergleich zum Vorjahr' WHERE ad_process_id = 540558 AND (description IS NULL OR description = '');
UPDATE ad_process_trl SET description = 'Revenue report by period with year-over-year comparison', istranslated = 'Y' WHERE ad_process_id = 540558 AND ad_language = 'en_US' AND (description IS NULL OR description = '');

-- 540561 Umsatzreport Geschäftspartner
UPDATE ad_process SET description = 'Umsatzreport gruppiert nach Geschäftspartner mit Periodenvergleich' WHERE ad_process_id = 540561 AND (description IS NULL OR description = '');
UPDATE ad_process_trl SET description = 'Revenue report grouped by business partner with period comparison', istranslated = 'Y' WHERE ad_process_id = 540561 AND ad_language = 'en_US' AND (description IS NULL OR description = '');

-- 540740 Umsatzreport Geschäftspartner mit Menge
UPDATE ad_process SET description = 'Umsatzreport gruppiert nach Geschäftspartner mit Mengen und Periodenvergleich' WHERE ad_process_id = 540740 AND (description IS NULL OR description = '');
UPDATE ad_process_trl SET description = 'Revenue report grouped by business partner with quantities and period comparison', istranslated = 'Y' WHERE ad_process_id = 540740 AND ad_language = 'en_US' AND (description IS NULL OR description = '');

-- 540754 Umsatzreport Woche
UPDATE ad_process SET description = 'Umsatzreport nach Kalenderwoche mit Wochenvergleich' WHERE ad_process_id = 540754 AND (description IS NULL OR description = '');
UPDATE ad_process_trl SET description = 'Revenue report by calendar week with week-over-week comparison', istranslated = 'Y' WHERE ad_process_id = 540754 AND ad_language = 'en_US' AND (description IS NULL OR description = '');

-- 540544 Umsatzliste: fix EN translation (currently shows DE text)
UPDATE ad_process_trl SET name = 'Revenue List', description = 'Prints a revenue list based on invoice candidates', istranslated = 'Y' WHERE ad_process_id = 540544 AND ad_language = 'en_US';

-- ============================================================================
-- Parameter names and descriptions
-- ============================================================================

-- ---------- 540754 Umsatzreport Woche: fix "Week" → "Basiswoche"/"Vergleichswoche" ----------

-- Base_Week (541142)
UPDATE ad_process_para SET name = 'Basiswoche', description = 'Kalenderwoche des Basiszeitraums' WHERE ad_process_para_id = 541142;
UPDATE ad_process_para_trl SET name = 'Base Week', description = 'Calendar week of the base period', istranslated = 'Y' WHERE ad_process_para_id = 541142 AND ad_language = 'en_US';

-- Comp_Week (541144)
UPDATE ad_process_para SET name = 'Vergleichswoche', description = 'Kalenderwoche des Vergleichszeitraums' WHERE ad_process_para_id = 541144;
UPDATE ad_process_para_trl SET name = 'Comparison Week', description = 'Calendar week of the comparison period', istranslated = 'Y' WHERE ad_process_para_id = 541144 AND ad_language = 'en_US';

-- Base_Year (541141): add clarifying name
UPDATE ad_process_para SET name = 'Basisjahr', description = 'Kalenderjahr des Basiszeitraums' WHERE ad_process_para_id = 541141;
UPDATE ad_process_para_trl SET name = 'Base Year', description = 'Calendar year of the base period', istranslated = 'Y' WHERE ad_process_para_id = 541141 AND ad_language = 'en_US';

-- Comp_Year (541143): add clarifying name
UPDATE ad_process_para SET name = 'Vergleichsjahr', description = 'Kalenderjahr des Vergleichszeitraums' WHERE ad_process_para_id = 541143;
UPDATE ad_process_para_trl SET name = 'Comparison Year', description = 'Calendar year of the comparison period', istranslated = 'Y' WHERE ad_process_para_id = 541143 AND ad_language = 'en_US';

-- ---------- 540561 Umsatzreport Geschäftspartner: period parameters ----------

-- Base_Period_Start (540671)
UPDATE ad_process_para SET description = 'Startperiode des Basiszeitraums' WHERE ad_process_para_id = 540671;
UPDATE ad_process_para_trl SET name = 'Base Period From', description = 'Start period of the base timeframe', istranslated = 'Y' WHERE ad_process_para_id = 540671 AND ad_language = 'en_US';

-- Base_Period_End (540672)
UPDATE ad_process_para SET description = 'Endperiode des Basiszeitraums' WHERE ad_process_para_id = 540672;
UPDATE ad_process_para_trl SET name = 'Base Period To', description = 'End period of the base timeframe', istranslated = 'Y' WHERE ad_process_para_id = 540672 AND ad_language = 'en_US';

-- Comp_Period_Start (540673)
UPDATE ad_process_para SET description = 'Startperiode des Vergleichszeitraums (optional)' WHERE ad_process_para_id = 540673;
UPDATE ad_process_para_trl SET name = 'Comparison Period From', description = 'Start period of the comparison timeframe (optional)', istranslated = 'Y' WHERE ad_process_para_id = 540673 AND ad_language = 'en_US';

-- Comp_Period_End (540674)
UPDATE ad_process_para SET description = 'Endperiode des Vergleichszeitraums (optional)' WHERE ad_process_para_id = 540674;
UPDATE ad_process_para_trl SET name = 'Comparison Period To', description = 'End period of the comparison timeframe (optional)', istranslated = 'Y' WHERE ad_process_para_id = 540674 AND ad_language = 'en_US';

-- ---------- 540740 Umsatzreport Geschäftspartner mit Menge: same period parameters ----------

-- Base_Period_Start (541053)
UPDATE ad_process_para SET description = 'Startperiode des Basiszeitraums' WHERE ad_process_para_id = 541053;
UPDATE ad_process_para_trl SET name = 'Base Period From', description = 'Start period of the base timeframe', istranslated = 'Y' WHERE ad_process_para_id = 541053 AND ad_language = 'en_US';

-- Base_Period_End (541054)
UPDATE ad_process_para SET description = 'Endperiode des Basiszeitraums' WHERE ad_process_para_id = 541054;
UPDATE ad_process_para_trl SET name = 'Base Period To', description = 'End period of the base timeframe', istranslated = 'Y' WHERE ad_process_para_id = 541054 AND ad_language = 'en_US';

-- Comp_Period_Start (541055)
UPDATE ad_process_para SET description = 'Startperiode des Vergleichszeitraums (optional)' WHERE ad_process_para_id = 541055;
UPDATE ad_process_para_trl SET name = 'Comparison Period From', description = 'Start period of the comparison timeframe (optional)', istranslated = 'Y' WHERE ad_process_para_id = 541055 AND ad_language = 'en_US';

-- Comp_Period_End (541056)
UPDATE ad_process_para SET description = 'Endperiode des Vergleichszeitraums (optional)' WHERE ad_process_para_id = 541056;
UPDATE ad_process_para_trl SET name = 'Comparison Period To', description = 'End period of the comparison timeframe (optional)', istranslated = 'Y' WHERE ad_process_para_id = 541056 AND ad_language = 'en_US';

-- ---------- ReportFormat: fix name and add description (all 4 processes) ----------

UPDATE ad_process_para SET name = 'Berichtsformat', description = 'Ausgabeformat: Standard oder Tabellenansicht' WHERE ad_process_para_id IN (541685, 541678, 541695, 541694);
UPDATE ad_process_para_trl SET name = 'Report Format', description = 'Output format: Standard or Tabular view', istranslated = 'Y' WHERE ad_process_para_id IN (541685, 541678, 541695, 541694) AND ad_language = 'en_US';

-- ---------- 540544 Umsatzliste: fix parameter descriptions ----------

-- StartDate (540643): replace EN-only description with DE
UPDATE ad_process_para SET description = 'Erster Tag des Berichtszeitraums (inklusive)' WHERE ad_process_para_id = 540643;

-- EndDate (540644): replace EN-only description with DE
UPDATE ad_process_para SET description = 'Letzter Tag des Berichtszeitraums (inklusive)' WHERE ad_process_para_id = 540644;
-- Also fix wrong EN name "Contract End" → "End Date"
UPDATE ad_process_para_trl SET name = 'End Date', description = 'Last day of the report period (inclusive)', istranslated = 'Y' WHERE ad_process_para_id = 540644 AND ad_language = 'en_US';
UPDATE ad_process_para_trl SET description = 'First day of the report period (inclusive)', istranslated = 'Y' WHERE ad_process_para_id = 540643 AND ad_language = 'en_US';

-- showdetails (540646)
UPDATE ad_process_para SET description = 'Wenn Ja, werden einzelne Produkte aufgelistet' WHERE ad_process_para_id = 540646;
UPDATE ad_process_para_trl SET name = 'Show Product Details', description = 'If Yes, individual products are listed', istranslated = 'Y' WHERE ad_process_para_id = 540646 AND ad_language = 'en_US';
