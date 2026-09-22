import {
  getDisabledStandardActions,
  getEnabledStandardActions,
  getStandardActions,
} from '../../../components/header/SubHeader';
import { DocumentAction } from '../../../constants/DocumentAction';
import { getTableId, initialTableState } from '../../../reducers/tables';

const windowId = '123';
const viewId = 'view-1';
const tableId = getTableId({ windowId, viewId });

const newRefusedByRole = {
  action: DocumentAction.NEW_DOCUMENT,
  reason: 'Die Rolle darf keine neuen Datensätze anlegen',
  reasonKey: 'de.metas.ui.web.RoleCreateNotAllowed',
};

const createViewState = (tableProps) => ({
  tables: {
    [tableId]: {
      ...initialTableState,
      windowId,
      viewId,
      ...tableProps,
    },
  },
  length: 1,
});

describe('SubHeader standard action selectors', () => {
  describe('on the view (grid) route', () => {
    it('Should keep New in the standard actions when the role refuses it with a reason', () => {
      const state = createViewState({
        allowNew: false,
        disabledStandardActions: [newRefusedByRole],
      });

      expect(getStandardActions({ state, windowId, viewId })).toEqual([
        DocumentAction.NEW_DOCUMENT,
      ]);
    });

    it('Should serve the disabled New action with its reason', () => {
      const state = createViewState({
        allowNew: false,
        disabledStandardActions: [newRefusedByRole],
      });

      expect(getDisabledStandardActions({ state, windowId, viewId })).toEqual([
        newRefusedByRole,
      ]);
    });

    it('Should not report New as enabled while it is disabled', () => {
      const state = createViewState({
        allowNew: false,
        disabledStandardActions: [newRefusedByRole],
      });

      expect(getEnabledStandardActions({ state, windowId, viewId })).toEqual(
        []
      );
    });

    it('Should report New as enabled for an unrestricted role', () => {
      const state = createViewState({ allowNew: true });

      expect(getDisabledStandardActions({ state, windowId, viewId })).toEqual(
        []
      );
      expect(getEnabledStandardActions({ state, windowId, viewId })).toEqual([
        DocumentAction.NEW_DOCUMENT,
      ]);
    });
  });
});
