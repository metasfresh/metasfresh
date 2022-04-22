CREATE TABLE backup.c_flatrate_term_2022_03_31 AS
SELECT *
FROM c_flatrate_term
;

UPDATE c_flatrate_term
SET Bill_Location_ID = newLocations.c_bpartner_location_id,
    updatedby        = 100,
    updated          = TO_TIMESTAMP('2022-03-31 17:00:00', 'YYYY-MM-DD HH24:MI:SS')
FROM (SELECT c_bpartner_id, c_bpartner_location_id, validFrom
      FROM c_bpartner_location bpl
      WHERE previous_id IS NOT NULL
        AND isActive = 'Y'
        AND NOT EXISTS(SELECT 1 FROM c_bpartner_location bpl2 WHERE bpl2.previous_id = bpl.c_bpartner_location_id)
        AND validfrom <= NOW() OR validFrom is null) newLocations
WHERE bill_bpartner_id = newLocations.c_bpartner_id
  AND (startDate <= newLocations.validFrom OR newLocations.validFrom IS NULL)
-- AND startDate >= '2021-06-01' --sanity check, when this feature was introduced
;

UPDATE c_flatrate_term
SET dropship_location_id = newLocations.c_bpartner_location_id,
    updatedby        = 100,
    updated          = TO_TIMESTAMP('2022-03-31 17:00:00', 'YYYY-MM-DD HH24:MI:SS')
FROM (SELECT c_bpartner_id, c_bpartner_location_id, validFrom
      FROM c_bpartner_location bpl
      WHERE previous_id IS NOT NULL
          AND isActive = 'Y'
          AND NOT EXISTS(SELECT 1 FROM c_bpartner_location bpl2 WHERE bpl2.previous_id = bpl.c_bpartner_location_id)
          AND validfrom <= NOW() OR validFrom is null) newLocations
WHERE dropship_bpartner_id = newLocations.c_bpartner_id
  AND (startDate <= newLocations.validFrom OR newLocations.validFrom IS NULL)
-- AND startDate >= '2021-06-01' --sanity check, when this feature was introduced
;