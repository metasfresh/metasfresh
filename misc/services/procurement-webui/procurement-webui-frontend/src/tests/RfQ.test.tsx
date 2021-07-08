import React from 'react';
import { render } from 'enzyme';
import RfQ from '../components/RfQ';
import './testsSetup'; // this is where the adapter is configured

test('renders RfQ correctly', () => {
  const wrapper = render(
    <RfQ id="1" name="Test Product" dateStart="01.01.2021" dateEnd="01.09.2021" quantityPromised="27 Kg" />
  );
  const html = wrapper.html();
  expect(html).toContain('<div class="column is-size-4-mobile no-p">Test Product</div>');
  expect(html).toContain('<div class="column is-size-7 no-p">01.01.2021 - 01.09.2021</div>');
  expect(html).toContain('<div class="column mt-2 is-size-2-mobile no-p">27 Kg</div>');
  expect(html).toContain('<span><i class="fas fa-check"></i></span>');
});
