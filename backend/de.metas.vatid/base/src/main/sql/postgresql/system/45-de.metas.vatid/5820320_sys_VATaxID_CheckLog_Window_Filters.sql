-- USt-IdNr.-Prüfprotokoll window (AD_Window 542183, AD_Tab 549365, AD_Table VATaxID_CheckLog 542639).
-- Three grid/filter tweaks. AD_Column ids from the table's creation migration:
--   VATaxID 593174, RequestDate 593176, ResponseDate 593177, IsActive 593167.

-- (1) VATaxID becomes a filter column.
UPDATE AD_Column SET IsSelectionColumn = 'Y', Updated = now(), UpdatedBy = 100
WHERE AD_Column_ID = 593174;

-- (2) The two existing date filters become date-RANGE (from / to) filters. The WebUI switch is
--     AD_Column.FilterOperator = 'B' (Between) -> a from/to widget in the filter panel. (IsRangeFilter is
--     inert in the WebUI -- no read-site -- so it is deliberately NOT used here.)
UPDATE AD_Column SET FilterOperator = 'B', Updated = now(), UpdatedBy = 100
WHERE AD_Column_ID IN (593176, 593177);

-- (3) IsActive: move the grid column to the far right (grid order = AD_UI_Element.SeqNoGrid, mirrored on
--     AD_Field.SeqNoGrid), and move its filter to the bottom of the filter panel.
--     Filter-panel order sorts on AD_Column.SelectionColumnSeqNo, where a NULL is treated as
--     Integer.MAX_VALUE (i.e. sorts LAST); every filter column on this tab leaves it NULL, so they all
--     tie at MAX and the order falls through to AD_Field.SeqNo. Setting AD_Field.SeqNo = 200 (below) is
--     therefore exactly what puts IsActive last. SelectionColumnSeqNo is deliberately NOT set: a finite
--     value there would sort BEFORE the NULL siblings and push IsActive FIRST -- the opposite of intended.
--     Deliberate deviation from the "Organisation last in the grid" convention: the change was explicitly
--     requested -- Aktiv is to sit at the far right.
UPDATE AD_Field SET SeqNo = 200, SeqNoGrid = 200, Updated = now(), UpdatedBy = 100
WHERE AD_Column_ID = 593167 AND AD_Tab_ID = 549365;

UPDATE AD_UI_Element SET SeqNoGrid = 200, Updated = now(), UpdatedBy = 100
WHERE AD_Field_ID IN (SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID = 593167 AND AD_Tab_ID = 549365);
