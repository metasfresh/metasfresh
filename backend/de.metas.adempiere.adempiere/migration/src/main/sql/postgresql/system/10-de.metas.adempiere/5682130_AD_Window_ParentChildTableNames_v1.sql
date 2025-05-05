DROP VIEW IF EXISTS AD_Window_ParentChildTableNames_v1
;

CREATE OR REPLACE VIEW AD_Window_ParentChildTableNames_v1 AS
SELECT AD_Window_ID
     , WindowName
     , ParentTableName
     , Parent_Table_IsEnableRemoteCacheInvalidation
     , ChildTableName
     , Child_Table_IsEnableRemoteCacheInvalidation
     , ParentColumnName AS ParentLinkColumnName
     , ChildKeyColumnName
     --
     , (CASE
            WHEN ColumnName IS NOT NULL THEN ColumnName
                                        ELSE (SELECT c.ColumnName FROM AD_Column c WHERE c.AD_Table_ID = t.Child_Table_ID AND c.ColumnName = t.ParentColumnName AND c.IsActive = 'Y')
        END)            AS ChildLinkColumnName
     --
FROM (SELECT w.AD_Window_ID
           , w.Name                                                                                                             AS WindowName
           , pt.TableName                                                                                                       AS ParentTableName
           -- , pt.AD_Table_ID                                                                                                     AS Parent_Table_ID
           , (CASE
                  WHEN w.IsEnableRemoteCacheInvalidation = 'Y' OR pt.IsEnableRemoteCacheInvalidation = 'Y' THEN 'Y'
                                                                                                           ELSE 'N'
              END)                                                                                                              AS Parent_Table_IsEnableRemoteCacheInvalidation
           , ct.TableName                                                                                                       AS ChildTableName
           , ct.AD_Table_ID                                                                                                     AS Child_Table_ID
           , (CASE
                  WHEN ct.AD_Table_ID IS NULL                                                              THEN NULL
                  WHEN w.IsEnableRemoteCacheInvalidation = 'Y' OR ct.IsEnableRemoteCacheInvalidation = 'Y' THEN 'Y'
                                                                                                           ELSE 'N'
              END)                                                                                                              AS Child_Table_IsEnableRemoteCacheInvalidation
           -- ColumnName
           , (CASE
                  WHEN ctt.AD_Column_ID IS NOT NULL THEN (SELECT c.ColumnName FROM AD_Column c WHERE c.AD_Column_ID = ctt.AD_Column_ID)
              END)                                                                                                              AS ColumnName

           -- ParentColumnName
           , (CASE
                  WHEN ctt.Parent_Column_ID IS NOT NULL THEN (SELECT c.ColumnName FROM AD_Column c WHERE c.AD_Column_ID = ctt.Parent_Column_ID)
                                                        ELSE (SELECT c.ColumnName FROM AD_Column c WHERE c.AD_Table_ID = ptt.AD_Table_ID AND c.IsKey = 'Y' AND c.IsActive = 'Y')
              END)                                                                                                              AS ParentColumnName

           , (SELECT c.ColumnName FROM AD_Column c WHERE c.AD_Table_ID = ct.AD_Table_ID AND c.IsKey = 'Y' AND c.IsActive = 'Y') AS ChildKeyColumnName

      FROM AD_Tab ptt/*parent-tab*/
               INNER JOIN AD_Table pt/*parent-table*/ ON (pt.AD_Table_ID = ptt.AD_Table_ID)
               LEFT JOIN AD_Tab ctt/*child-tab*/ ON (
                  ctt.AD_Window_ID = ptt.AD_Window_ID
              AND ctt.TabLevel = ptt.TabLevel + 1
              AND ctt.SeqNo > ptt.SeqNo
              AND ctt.IsActive = 'Y'
          )
               LEFT JOIN AD_Table ct/*child-table*/ ON (ct.AD_Table_ID = ctt.AD_Table_ID)
               INNER JOIN AD_Window w ON (w.AD_Window_ID = ptt.AD_Window_ID)
      WHERE TRUE
          /* the parent tab needs to have the smallest SeqNo and TabLevel (but not neccesarly 10 resp. 0) */
        AND ptt.SeqNo = (SELECT MIN(siblings.SeqNo) FROM AD_Tab siblings WHERE siblings.AD_Window_ID = w.AD_Window_ID AND siblings.IsActive = 'Y')
        AND ptt.TabLevel = (SELECT MIN(siblings.TabLevel) FROM AD_Tab siblings WHERE siblings.AD_Window_ID = w.AD_Window_ID AND siblings.IsActive = 'Y')
        AND ptt.IsActive = 'Y'
        AND w.IsActive = 'Y'
        AND pt.IsActive = 'Y'
        AND COALESCE(ct.IsActive, 'Y') = 'Y'
         -- AND EXISTS(SELECT 1 FROM AD_Menu m WHERE m.AD_Window_ID = w.AD_Window_ID AND m.IsActive = 'Y') -- allow cache invalidation even if not in menu
     ) t
;

/*
select * from AD_Window_ParentChildTableNames_v1
where true
order by parentTableName, AD_Window_ID, childTableName;
*/
