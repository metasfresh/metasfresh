-- USt-IdNr.-Prüfprotokoll window (AD_Window 542183, AD_Tab 549365, AD_Table VATaxID_CheckLog 542639).
-- Make the Business Partner and Address fields SEARCH fields instead of TableDir drop-downs.
--
-- Both C_BPartner_ID (AD_Column 593172) and C_BPartner_Location_ID (593173) were AD_Reference_ID = 19
-- (TableDir), so their lookups render as full drop-downs over every business partner / location. On a
-- real instance (tens of thousands of partners) that is unusable — C_BPartner_ID is grid-displayed and
-- a selection/filter column, so both its display resolution and its filter widget are affected.
-- AD_Reference_ID = 30 (Search) gives the on-demand search widget instead.
--
-- Search references (standard general lookups, table references over the respective table):
--   C_BPartner_ID          -> 138 'C_BPartner (Trx)'   (where IsSummary='N' AND IsActive='Y')
--   C_BPartner_Location_ID -> 159 'C_BPartner Location'
--
-- Core-only: window 542183 is a core audit window with no override window, so it applies everywhere.

UPDATE AD_Column
   SET AD_Reference_ID       = 30,
       AD_Reference_Value_ID = 138,
       Updated               = now(),
       UpdatedBy             = 100
WHERE AD_Column_ID = 593172;

UPDATE AD_Column
   SET AD_Reference_ID       = 30,
       AD_Reference_Value_ID = 159,
       Updated               = now(),
       UpdatedBy             = 100
WHERE AD_Column_ID = 593173;
