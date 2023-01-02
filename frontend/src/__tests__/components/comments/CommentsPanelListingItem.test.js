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

import React from "react";
import { render } from "enzyme";
import CommentsPanelListingItem
  from '../../../components/comments/CommentsPanelListingItem';

describe("ComponentsPanelListingItem test", () => {
  it("renders without errors", () => {
    const testProps = {
      data: {
        "createdBy": "branchtest",
        "created": "2020-04-28T06:55:19.000+02:00",
        "text": "My first comment"
      }
    };
    const wrapper = render(
          <CommentsPanelListingItem {...testProps} />

    );

    const html = wrapper.html();
    expect(html).not.toBe(null);
    expect(html).toContain('My first comment')
    expect(html).toContain('04/28/2020')
    expect(html).toContain('branchtest')
  });


});
