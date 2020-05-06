import React from 'react';
import { shallow, render } from 'enzyme';
import renderer from 'react-test-renderer';

import fixtures from '../../../../test_setup/fixtures/table_item_props.json'

import TableItem from '../../../components/table/TableItem';

function createInitProps(propsSeed = fixtures.oldProps1, customProps){
  return {
    ...propsSeed,
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

describe('Table row (TableItem)', () => {
  it('renders without errors', () => {
    const props = createInitProps();;

    shallow(<TableItem {...props} />);
  });

  it('output matches snapshot', () => {
    const props = createInitProps();

    const component = renderer.create(<TableItem {...props} />);
    const tree = component.toJSON();
    expect(tree).toMatchSnapshot();
  });

  it('row re-renders after data changed', () => {
    const props = createInitProps();
    const updatedProps = createInitProps(fixtures.newProps1);
    const table = document.createElement('table');
    const tbody = document.createElement('tbody');
    table.appendChild(tbody);

    const wrapper = mount(<TableItem {...props} />, { attachTo: tbody });

    expect(wrapper.find('.row-1 [data-cy="cell-QtyEntered"]').text()).toEqual('3');

    wrapper.setProps(updatedProps);

    expect(wrapper.find('.row-1 [data-cy="cell-QtyEntered"]').text()).toEqual('4');
  });
});