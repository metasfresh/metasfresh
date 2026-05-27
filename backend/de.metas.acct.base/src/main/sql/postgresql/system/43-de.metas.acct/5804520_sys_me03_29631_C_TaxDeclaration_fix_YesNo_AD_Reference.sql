-- AD_Reference_ID=20 is the WebUI-supported Yes-No reference; 319 fails widget rendering.
UPDATE AD_Column SET AD_Reference_ID = 20
WHERE AD_Column_ID IN (
    592616 /*From ID Server*/, -- IsCorrection
    592618 /*From ID Server*/  -- IsCorrectionNeeded
);
