
CREATE TABLE backup.EDI_DesadvLine_20200914 AS SELECT * FROM EDI_DesadvLine;

UPDATE EDI_DesadvLine dl
SET QtyItemCapacity=ol.QtyItemCapacity, UpdatedBy=99, Updated='2020-09-14 06:59:03.797212 +02:00'
FROM C_OrderLine ol
WHERE true
  AND dl.QtyItemCapacity IS NULL
  AND ol.QtyItemCapacity IS NOT NULL
  AND ol.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID
;
