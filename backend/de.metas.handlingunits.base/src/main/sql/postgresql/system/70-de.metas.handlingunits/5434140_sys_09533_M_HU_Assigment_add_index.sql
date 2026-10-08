
DROP INDEX IF EXISTS m_hu_assignment_M_LU_HU_ID;
CREATE INDEX m_hu_assignment_M_LU_HU_ID
  ON m_hu_assignment
  USING btree
  (M_LU_HU_ID );

DROP INDEX IF EXISTS m_hu_assignment_M_TU_HU_ID;
CREATE INDEX m_hu_assignment_M_TU_HU_ID
  ON m_hu_assignment
  USING btree
  (M_TU_HU_ID );

DROP INDEX IF EXISTS m_hu_assignment_VHU_ID;
CREATE INDEX m_hu_assignment_VHU_ID
  ON m_hu_assignment
  USING btree
  (VHU_ID );
