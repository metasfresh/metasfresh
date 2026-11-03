-- Repairs the primary keys written by 5818170_sys_gh31399_FixProductWindowAndLabelsTab.sql.
--
-- 5818170 is a Swing-client recording. It inserted its new AD_Field / AD_UI_Column /
-- AD_UI_ElementGroup / AD_UI_Element rows with IDs taken from the recording developer's
-- LOCAL native sequence (>= 1000000) instead of IDs allocated from the central ID server.
-- The native sequences ad_field_seq / ad_ui_column_seq / ad_ui_elementgroup_seq /
-- ad_ui_element_seq all start at 1000000, so those IDs are exactly the numbers every
-- instance's own sequence hands out to its own, unrelated rows: the recorded rows squat on
-- that space and collide with locally created rows (observed on a customer instance that had
-- independently allocated the same IDs).
--
-- 5818170 itself has been corrected to use the ID-server IDs below, so a fresh database gets
-- them right. This script fixes the instances on which 5818170 already ran with the wrong IDs.
--
--   AD_Field            1000008 -> 784918   (AD_Tab_ID=549361, AD_Column_ID=593114)
--   AD_Field            1000009 -> 784919   (AD_Tab_ID=549361, AD_Column_ID=593111)
--   AD_Field            1000010 -> 784920   (AD_Tab_ID=549361, AD_Column_ID=593110)
--   AD_UI_Column        1000033 -> 549754   (AD_UI_Section_ID=547866, SeqNo=20)
--   AD_UI_ElementGroup  1000047 -> 555763   (that UI column, Name='main')
--   AD_UI_Element       1000528 -> 654688   (AD_Tab_ID=180, group 1000039, Name='Tag')
--   AD_UI_Element       1000529 -> 654689   (AD_Tab_ID=549361, the field that was 1000008)
--   AD_UI_Element       1000530 -> 654690   (AD_Tab_ID=549361, the field that was 1000009)
--   AD_UI_Element       1000531 -> 654691   (AD_Tab_ID=549361, the field that was 1000010)
--
-- All replacement IDs are /*From ID Server*/.
--
-- AD_UI_ElementGroup 1000039 and AD_UI_Column 1000004 are REFERENCED by 5818170 but not
-- inserted by it: they are 2016-vintage rows that ship inside the shared seed dump, i.e. they
-- are identical on every instance. They are NOT renumbered here.
--
-- Properties of this script:
--   * every row is located by its NATURAL KEY, never by the wrong primary key (the primary key
--     is the thing being corrected);
--   * it is a no-op when the row already carries the correct ID, and a no-op on an instance
--     where 5818170 never ran -- so it is fully idempotent and re-runnable;
--   * it refuses (raises) rather than guesses if a row carries a third, unexpected ID, or if
--     the target ID is already taken;
--   * every foreign key onto the four parent tables is repointed in the same transaction. The
--     reference list is the complete set of FK constraints on the four parents (pg_constraint
--     where confrelid in (ad_field, ad_ui_column, ad_ui_elementgroup, ad_ui_element)); all of
--     them are DEFERRABLE INITIALLY DEFERRED with ON UPDATE NO ACTION, so parent and children
--     may be updated in any order inside the transaction and the check happens at COMMIT.
--     None of ad_ui_column / ad_ui_elementgroup / ad_ui_element has a _Trl companion; only
--     ad_field does (ad_field_trl).
--
-- Created / Updated / UpdatedBy are deliberately NOT stamped: this corrects a row's identity,
-- not its content. Bumping AD_Field.Updated without bumping AD_Field_Trl.Updated would flip the
-- `f_trl.updated <> e_trl.updated` guard inside update_FieldTranslation_From_AD_Name_Element and
-- silently change translation-propagation behaviour, which is not what this fix is about.


-- Defensive backup of the operator-configurable children, taken once and only when there is
-- actually something to renumber. The AD_* structural metadata tables themselves are schema
-- definitions and are exempt from the backup rule.
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM AD_Field WHERE AD_Field_ID IN (1000008, 1000009, 1000010))
    THEN
        PERFORM backup_table('ad_userdef_field',      '_gh31399_renumber');
        PERFORM backup_table('ad_user_sortpref_line', '_gh31399_renumber');
        PERFORM backup_table('ad_field_contextmenu',  '_gh31399_renumber');
    END IF;
END $$;


-- AD_Field: 1000008 -> 784918, 1000009 -> 784919, 1000010 -> 784920.
-- Natural key: (AD_Tab_ID, AD_Column_ID) -- enforced unique by index ad_field_column.
DO $$
DECLARE
    r        RECORD;
    v_cur_id numeric(10,0);
BEGIN
    FOR r IN
        SELECT *
        FROM (VALUES
                  (549361, 593114, 1000008, 784918 /*From ID Server*/),
                  (549361, 593111, 1000009, 784919 /*From ID Server*/),
                  (549361, 593110, 1000010, 784920 /*From ID Server*/)
             ) AS t(ad_tab_id, ad_column_id, wrong_id, new_id)
    LOOP
        SELECT f.AD_Field_ID INTO v_cur_id
        FROM AD_Field f
        WHERE f.AD_Tab_ID = r.ad_tab_id AND f.AD_Column_ID = r.ad_column_id;

        IF v_cur_id IS NULL THEN
            RAISE NOTICE 'AD_Field (AD_Tab_ID=%, AD_Column_ID=%) does not exist - 5818170 never ran here; nothing to do', r.ad_tab_id, r.ad_column_id;
            CONTINUE;
        END IF;

        IF v_cur_id = r.new_id THEN
            RAISE NOTICE 'AD_Field (AD_Tab_ID=%, AD_Column_ID=%) already carries % - nothing to do', r.ad_tab_id, r.ad_column_id, r.new_id;
            CONTINUE;
        END IF;

        IF v_cur_id <> r.wrong_id THEN
            RAISE EXCEPTION 'AD_Field (AD_Tab_ID=%, AD_Column_ID=%) carries the unexpected AD_Field_ID % (expected % or %) - refusing to renumber', r.ad_tab_id, r.ad_column_id, v_cur_id, r.wrong_id, r.new_id;
        END IF;

        IF EXISTS (SELECT 1 FROM AD_Field WHERE AD_Field_ID = r.new_id) THEN
            RAISE EXCEPTION 'AD_Field % is already taken - cannot renumber % to it', r.new_id, r.wrong_id;
        END IF;

        -- the eight foreign keys onto AD_Field; note AD_UI_Element references it TWICE
        UPDATE AD_Field_Trl          SET AD_Field_ID              = r.new_id WHERE AD_Field_ID              = r.wrong_id;
        UPDATE AD_Element_Link       SET AD_Field_ID              = r.new_id WHERE AD_Field_ID              = r.wrong_id;
        UPDATE AD_Field_ContextMenu  SET AD_Field_ID              = r.new_id WHERE AD_Field_ID              = r.wrong_id;
        UPDATE AD_UI_Element         SET AD_Field_ID              = r.new_id WHERE AD_Field_ID              = r.wrong_id;
        UPDATE AD_UI_Element         SET Labels_Selector_Field_ID = r.new_id WHERE Labels_Selector_Field_ID = r.wrong_id;
        UPDATE AD_UI_ElementField    SET AD_Field_ID              = r.new_id WHERE AD_Field_ID              = r.wrong_id;
        UPDATE AD_User_SortPref_Line SET AD_Field_ID              = r.new_id WHERE AD_Field_ID              = r.wrong_id;
        UPDATE AD_UserDef_Field      SET AD_Field_ID              = r.new_id WHERE AD_Field_ID              = r.wrong_id;

        UPDATE AD_Field SET AD_Field_ID = r.new_id WHERE AD_Field_ID = r.wrong_id;

        RAISE NOTICE 'AD_Field % renumbered to %', r.wrong_id, r.new_id;
    END LOOP;
END $$;


-- AD_UI_Column: 1000033 -> 549754.
-- Natural key: (AD_UI_Section_ID, SeqNo). AD_UI_Section_ID=547866 alone is NOT unique -- that
-- section carries a second UI column at SeqNo=10 -- so SeqNo is part of the key.
DO $$
DECLARE
    v_cur_id numeric(10,0);
    v_wrong  numeric(10,0) := 1000033;
    v_new    numeric(10,0) := 549754 /*From ID Server*/;
BEGIN
    SELECT c.AD_UI_Column_ID INTO v_cur_id
    FROM AD_UI_Column c
    WHERE c.AD_UI_Section_ID = 547866 AND c.SeqNo = 20;

    IF v_cur_id IS NULL THEN
        RAISE NOTICE 'AD_UI_Column (AD_UI_Section_ID=547866, SeqNo=20) does not exist - 5818170 never ran here; nothing to do';
    ELSIF v_cur_id = v_new THEN
        RAISE NOTICE 'AD_UI_Column (AD_UI_Section_ID=547866, SeqNo=20) already carries % - nothing to do', v_new;
    ELSIF v_cur_id <> v_wrong THEN
        RAISE EXCEPTION 'AD_UI_Column (AD_UI_Section_ID=547866, SeqNo=20) carries the unexpected AD_UI_Column_ID % (expected % or %) - refusing to renumber', v_cur_id, v_wrong, v_new;
    ELSE
        IF EXISTS (SELECT 1 FROM AD_UI_Column WHERE AD_UI_Column_ID = v_new) THEN
            RAISE EXCEPTION 'AD_UI_Column % is already taken - cannot renumber % to it', v_new, v_wrong;
        END IF;

        -- the single foreign key onto AD_UI_Column
        UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID = v_new WHERE AD_UI_Column_ID = v_wrong;

        UPDATE AD_UI_Column SET AD_UI_Column_ID = v_new WHERE AD_UI_Column_ID = v_wrong;

        RAISE NOTICE 'AD_UI_Column % renumbered to %', v_wrong, v_new;
    END IF;
END $$;


-- AD_UI_ElementGroup: 1000047 -> 555763.
-- Natural key: (AD_UI_Column_ID, Name). The UI column is re-resolved from its own natural key
-- so this block does not depend on whether the block above had anything to do.
DO $$
DECLARE
    v_col_id numeric(10,0);
    v_cur_id numeric(10,0);
    v_wrong  numeric(10,0) := 1000047;
    v_new    numeric(10,0) := 555763 /*From ID Server*/;
BEGIN
    SELECT c.AD_UI_Column_ID INTO v_col_id
    FROM AD_UI_Column c
    WHERE c.AD_UI_Section_ID = 547866 AND c.SeqNo = 20;

    IF v_col_id IS NULL THEN
        RAISE NOTICE 'AD_UI_Column (AD_UI_Section_ID=547866, SeqNo=20) does not exist - 5818170 never ran here; nothing to do';
        RETURN;
    END IF;

    SELECT g.AD_UI_ElementGroup_ID INTO v_cur_id
    FROM AD_UI_ElementGroup g
    WHERE g.AD_UI_Column_ID = v_col_id AND g.Name = 'main';

    IF v_cur_id IS NULL THEN
        RAISE NOTICE 'AD_UI_ElementGroup (AD_UI_Column_ID=%, Name=main) does not exist - nothing to do', v_col_id;
    ELSIF v_cur_id = v_new THEN
        RAISE NOTICE 'AD_UI_ElementGroup (AD_UI_Column_ID=%, Name=main) already carries % - nothing to do', v_col_id, v_new;
    ELSIF v_cur_id <> v_wrong THEN
        RAISE EXCEPTION 'AD_UI_ElementGroup (AD_UI_Column_ID=%, Name=main) carries the unexpected AD_UI_ElementGroup_ID % (expected % or %) - refusing to renumber', v_col_id, v_cur_id, v_wrong, v_new;
    ELSE
        IF EXISTS (SELECT 1 FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID = v_new) THEN
            RAISE EXCEPTION 'AD_UI_ElementGroup % is already taken - cannot renumber % to it', v_new, v_wrong;
        END IF;

        -- the single foreign key onto AD_UI_ElementGroup
        UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID = v_new WHERE AD_UI_ElementGroup_ID = v_wrong;

        UPDATE AD_UI_ElementGroup SET AD_UI_ElementGroup_ID = v_new WHERE AD_UI_ElementGroup_ID = v_wrong;

        RAISE NOTICE 'AD_UI_ElementGroup % renumbered to %', v_wrong, v_new;
    END IF;
END $$;


-- AD_UI_Element: 1000528 -> 654688, 1000529 -> 654689, 1000530 -> 654690, 1000531 -> 654691.
-- Natural keys:
--   654688: (AD_Tab_ID=180, AD_UI_ElementGroup_ID=1000039, Name='Tag') -- group 1000039 is a
--           pre-existing seed row and is NOT renumbered, so it is used as a literal here;
--   654689..654691: (AD_Tab_ID=549361, AD_Field_ID), where the field is itself re-resolved from
--           its own (AD_Tab_ID, AD_Column_ID) natural key -- so this block works whether or not
--           the AD_Field block above renumbered anything.
DO $$
DECLARE
    r          RECORD;
    v_field_id numeric(10,0);
    v_cur_id   numeric(10,0);
BEGIN
    FOR r IN
        SELECT *
        FROM (VALUES
                  (180,    NULL::integer, 1000039::integer, 'Tag'::text, 1000528, 654688 /*From ID Server*/),
                  (549361, 593114,        NULL,             NULL,        1000529, 654689 /*From ID Server*/),
                  (549361, 593111,        NULL,             NULL,        1000530, 654690 /*From ID Server*/),
                  (549361, 593110,        NULL,             NULL,        1000531, 654691 /*From ID Server*/)
             ) AS t(ad_tab_id, ad_column_id, ad_ui_elementgroup_id, name, wrong_id, new_id)
    LOOP
        v_cur_id := NULL;

        IF r.ad_column_id IS NULL THEN
            SELECT e.AD_UI_Element_ID INTO v_cur_id
            FROM AD_UI_Element e
            WHERE e.AD_Tab_ID = r.ad_tab_id
              AND e.AD_UI_ElementGroup_ID = r.ad_ui_elementgroup_id
              AND e.Name = r.name;
        ELSE
            SELECT f.AD_Field_ID INTO v_field_id
            FROM AD_Field f
            WHERE f.AD_Tab_ID = r.ad_tab_id AND f.AD_Column_ID = r.ad_column_id;

            IF v_field_id IS NULL THEN
                RAISE NOTICE 'AD_Field (AD_Tab_ID=%, AD_Column_ID=%) does not exist - 5818170 never ran here; nothing to do', r.ad_tab_id, r.ad_column_id;
                CONTINUE;
            END IF;

            SELECT e.AD_UI_Element_ID INTO v_cur_id
            FROM AD_UI_Element e
            WHERE e.AD_Tab_ID = r.ad_tab_id AND e.AD_Field_ID = v_field_id;
        END IF;

        IF v_cur_id IS NULL THEN
            RAISE NOTICE 'AD_UI_Element for (AD_Tab_ID=%, AD_Column_ID=%, group=%, Name=%) does not exist - nothing to do', r.ad_tab_id, r.ad_column_id, r.ad_ui_elementgroup_id, r.name;
            CONTINUE;
        END IF;

        IF v_cur_id = r.new_id THEN
            RAISE NOTICE 'AD_UI_Element for (AD_Tab_ID=%, AD_Column_ID=%, group=%, Name=%) already carries % - nothing to do', r.ad_tab_id, r.ad_column_id, r.ad_ui_elementgroup_id, r.name, r.new_id;
            CONTINUE;
        END IF;

        IF v_cur_id <> r.wrong_id THEN
            RAISE EXCEPTION 'AD_UI_Element for (AD_Tab_ID=%, AD_Column_ID=%, group=%, Name=%) carries the unexpected AD_UI_Element_ID % (expected % or %) - refusing to renumber', r.ad_tab_id, r.ad_column_id, r.ad_ui_elementgroup_id, r.name, v_cur_id, r.wrong_id, r.new_id;
        END IF;

        IF EXISTS (SELECT 1 FROM AD_UI_Element WHERE AD_UI_Element_ID = r.new_id) THEN
            RAISE EXCEPTION 'AD_UI_Element % is already taken - cannot renumber % to it', r.new_id, r.wrong_id;
        END IF;

        -- the single foreign key onto AD_UI_Element
        UPDATE AD_UI_ElementField SET AD_UI_Element_ID = r.new_id WHERE AD_UI_Element_ID = r.wrong_id;

        UPDATE AD_UI_Element SET AD_UI_Element_ID = r.new_id WHERE AD_UI_Element_ID = r.wrong_id;

        RAISE NOTICE 'AD_UI_Element % renumbered to %', r.wrong_id, r.new_id;
    END LOOP;
END $$;
