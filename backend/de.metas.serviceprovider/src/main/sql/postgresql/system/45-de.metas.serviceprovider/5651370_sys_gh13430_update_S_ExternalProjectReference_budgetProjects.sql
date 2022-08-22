
--set S_ExternalProjectReference_Effort_ID for mf15 Budget projects
UPDATE S_ExternalProjectReference
SET S_ExternalProjectReference_Effort_ID = me03EffortProject.S_ExternalProjectReference_ID,
    UpdatedBy=99,
    Updated=now()
FROM (
         SELECT effortProject.S_ExternalProjectReference_ID
         FROM S_ExternalProjectReference effortProject
         WHERE effortProject.ProjectType = 'Effort'
           AND effortProject.ExternalReference = 'me03'
     ) me03EffortProject
WHERE ProjectType = 'Budget'
  AND ExternalReference = 'mf15'
  AND S_ExternalProjectReference_Effort_ID is null;