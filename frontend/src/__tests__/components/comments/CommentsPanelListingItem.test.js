import React from "react";
import { render } from "enzyme";
import CommentsPanelListingItem from '../../../components/comments/CommentsPanelListingItem';

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
