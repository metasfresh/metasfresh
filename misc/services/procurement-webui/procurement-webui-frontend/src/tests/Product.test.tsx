import React from 'react';
import { render } from 'enzyme';
import Product from '../components/Product';
import './testsSetup'; // this is where the adapter is configured

test('renders correctly the Product', () => {
  const wrapper = render(
    <Product
      id="2"
      productName="Test Name"
      packingInfo="packingInfo"
      qty={10}
      isEdited={true}
      confirmedByUser={true}
      editedItemsNo={10}
    />
  );
  const html = wrapper.html();
  expect(html).toContain('Test Name');
  expect(html).toContain('packingInfo');
  expect(html).toContain('<div class="column mt-2 is-size-2-mobile no-p has-text-right">10</div>');
  expect(html).toContain('<span><i class="fas fa-check"></i>');
});

test('when confirmedByUser ', () => {
  const wrapper = render(
    <Product
      id="2"
      productName="Test Name"
      packingInfo="packingInfo"
      qty={10}
      isEdited={true}
      confirmedByUser={false}
      editedItemsNo={10}
    />
  );
  const html = wrapper.html();
  expect(html).not.toContain('<span><i class="fas fa-check"></i>');
});
