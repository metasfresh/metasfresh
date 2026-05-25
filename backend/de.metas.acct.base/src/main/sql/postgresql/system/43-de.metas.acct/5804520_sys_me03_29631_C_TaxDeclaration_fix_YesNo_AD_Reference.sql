-- Tax Declaration: fix AD_Reference_ID on the IsCorrection and IsCorrectionNeeded columns.
-- Iter 7 of EPIC https://github.com/metasfresh/me03/issues/28717.
--
-- Migration 5804440 used AD_Reference_ID=319 (legacy _YesNo) for both Y/N columns. The
-- WebUI widget factory does not recognise 319 and fails with "Unknown displayType=319"
-- when opening the Tax Declaration window. The canonical Y/N reference in metasfresh
-- is AD_Reference_ID=20 (Yes-No) — that's what 14 of the 17 boolean columns in
-- de.metas.acct use.

UPDATE AD_Column SET AD_Reference_ID = 20
WHERE AD_Column_ID IN (
    592616 /*From ID Server*/, -- IsCorrection
    592618 /*From ID Server*/  -- IsCorrectionNeeded
);
