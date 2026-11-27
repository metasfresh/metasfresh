import TableWrapperWithClickOutside from '../../../components/table/TableWrapper';

const TableWrapper = TableWrapperWithClickOutside.getClass();

/**
 * @returns {Object} a TableWrapper instance whose setState applies the update at once, so the toggle's
 * resulting state can be read right after each call
 */
const createTableWrapper = () => {
  const tableWrapper = new TableWrapper({});
  tableWrapper.setState = (update) => {
    tableWrapper.state = { ...tableWrapper.state, ...update };
  };
  return tableWrapper;
};

/** @returns {{isBatchEntry: boolean, isBatchEntryOpenedAutomatically: boolean}} */
const batchEntryState = ({ state }) => ({
  isBatchEntry: state.isBatchEntry,
  isBatchEntryOpenedAutomatically: state.isBatchEntryOpenedAutomatically,
});

describe('TableWrapper.handleBatchEntryToggle: whether the batch entry was opened by the tab itself', () => {
  it('opened by the tab itself: flagged as automatic; closed again: the flag is cleared', () => {
    const tableWrapper = createTableWrapper();

    tableWrapper.handleBatchEntryToggle({ isAutomatic: true });
    expect(batchEntryState(tableWrapper)).toEqual({
      isBatchEntry: true,
      isBatchEntryOpenedAutomatically: true,
    });

    tableWrapper.handleBatchEntryToggle();
    expect(batchEntryState(tableWrapper)).toEqual({
      isBatchEntry: false,
      isBatchEntryOpenedAutomatically: false,
    });
  });

  it('opened by the batch entry button (click event): not automatic', () => {
    const tableWrapper = createTableWrapper();

    tableWrapper.handleBatchEntryToggle({ type: 'click', target: {} });

    expect(batchEntryState(tableWrapper)).toEqual({
      isBatchEntry: true,
      isBatchEntryOpenedAutomatically: false,
    });
  });

  it('opened by Alt+Q (no arguments): not automatic', () => {
    const tableWrapper = createTableWrapper();

    tableWrapper.handleBatchEntryToggle();

    expect(batchEntryState(tableWrapper)).toEqual({
      isBatchEntry: true,
      isBatchEntryOpenedAutomatically: false,
    });
  });

  it('closed by the tab itself while open: the flag is cleared', () => {
    const tableWrapper = createTableWrapper();
    tableWrapper.handleBatchEntryToggle({ isAutomatic: true });

    tableWrapper.handleBatchEntryToggle({ isAutomatic: true });

    expect(batchEntryState(tableWrapper)).toEqual({
      isBatchEntry: false,
      isBatchEntryOpenedAutomatically: false,
    });
  });
});
