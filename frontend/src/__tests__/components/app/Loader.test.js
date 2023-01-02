/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import React from 'react';
import { render } from 'enzyme';
import Loader from '../../../components/app/Loader';

describe('Loader test', () => {
  it('renders without errors', () => {
    const wrapper = render(<Loader />);

    const html = wrapper.html();
    expect(html).not.toBe(null);
  });

  it('renders normal loader', () => {
    const wrapper = render(<Loader />);

    const html = wrapper.html();
    expect(html.includes('meta-icon-settings')).toBe(true);
  });

  it('renders bootstrap loader', () => {
    const wrapper = render(<Loader loaderType="bootstrap" />);

    const html = wrapper.html();
    expect(html).not.toBe(null);
    expect(html.includes('spinner-border')).toBe(true);
  });
});
