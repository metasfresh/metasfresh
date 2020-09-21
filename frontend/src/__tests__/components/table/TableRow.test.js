import React from 'react';
import { shallow, mount } from 'enzyme';
import renderer from 'react-test-renderer';

import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import { getTableId } from '../../../reducers/tables';
import TableRow from '../../../components/table/TableRow';

function createInitProps(propsSeed = fixtures.oldProps1, customProps) {
  return {
    ...propsSeed,
    tableId: getTableId(propsSeed),
    onClick: jest.fn(),
    handleSelect: jest.fn(),
    onDoubleClick: jest.fn(),
    changeListenOnTrue: jest.fn(),
    handleRowCollapse: jest.fn(),
    handleRightClick: jest.fn(),
    onItemChange: jest.fn(),
    changeListenOnFalse: jest.fn(),
    getSizeClass: jest.fn(),
    ...customProps,
  };
}

describe('Table row (TableRow)', () => {
  it('renders without errors', () => {
    const props = createInitProps();

    shallow(<TableRow {...props} />);
  });

  it('output matches snapshot', () => {
    const props = createInitProps();

    const component = renderer.create(<TableRow {...props} />);
    const tree = component.toJSON();
    expect(tree).toMatchSnapshot();
  });

  it('row re-renders after data changed', () => {
    const props = createInitProps();
    const updatedProps = createInitProps(fixtures.newProps1);
    const table = document.createElement('table');
    const tbody = document.createElement('tbody');
    table.appendChild(tbody);

    const wrapper = mount(<TableRow {...props} />, { attachTo: tbody });

    expect(wrapper.find('.row-1 [data-cy="cell-QtyEntered"]').text()).toEqual(
      '3'
    );

    wrapper.setProps(updatedProps);

    expect(wrapper.find('.row-1 [data-cy="cell-QtyEntered"]').text()).toEqual(
      '4'
    );
  });
});
