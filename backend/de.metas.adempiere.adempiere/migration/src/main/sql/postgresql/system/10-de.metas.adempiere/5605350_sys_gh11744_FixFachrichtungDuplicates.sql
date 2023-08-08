CREATE TABLE backup.AD_User_Occupation_Job_gh1635 as select * from AD_User_Occupation_Job
;

CREATE TABLE backup.AD_User_Occupation_Specialization_gh1635 as select * from AD_User_Occupation_Specialization
;

CREATE TABLE backup.AD_User_Occupation_AdditionalSpecialization_gh1635 as select * from AD_User_Occupation_AdditionalSpecialization
;


delete from AD_User_Occupation_Job
using (
    select *
    from (
             select ad_user_id,
                    crm_occupation_id,
                    AD_User_Occupation_Job_id,
                    ROW_NUMBER()
                    OVER (
                        PARTITION BY ad_user_id,crm_occupation_id
                        order by ad_user_id) AS Row
             from AD_User_Occupation_Job d
         ) as d
    where row > 1
) duplicatesEntry
where duplicatesEntry.AD_User_Occupation_Job_id=AD_User_Occupation_Job.AD_User_Occupation_Job_id
;

delete from AD_User_Occupation_Specialization
using (
    select *
    from (
             select ad_user_id,
                    crm_occupation_id,
                    AD_User_Occupation_Specialization_id,
                    ROW_NUMBER()
                    OVER (
                        PARTITION BY ad_user_id,crm_occupation_id
                        order by ad_user_id) AS Row
             from AD_User_Occupation_Specialization d
         ) as d
    where row > 1
) duplicatesEntry
where duplicatesEntry.AD_User_Occupation_Specialization_id=AD_User_Occupation_Specialization.AD_User_Occupation_Specialization_id
;

delete from AD_User_Occupation_AdditionalSpecialization
using (
    select *
    from (
             select ad_user_id,
                    crm_occupation_id,
                    AD_User_Occupation_AdditionalSpecialization_id,
                    ROW_NUMBER()
                    OVER (
                        PARTITION BY ad_user_id,crm_occupation_id
                        order by ad_user_id) AS Row
             from AD_User_Occupation_AdditionalSpecialization d
         ) as d
    where row > 1
) duplicatesEntry
where duplicatesEntry.AD_User_Occupation_AdditionalSpecialization_id=AD_User_Occupation_AdditionalSpecialization.AD_User_Occupation_AdditionalSpecialization_id
;