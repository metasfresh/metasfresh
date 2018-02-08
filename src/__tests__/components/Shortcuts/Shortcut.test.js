import Shortcut from "../../../components/Shortcuts/Shortcut";

describe("Shortcut", () => {
  it("should return null from render()", () => {
    const shortcut = new Shortcut();

    expect(shortcut.render()).toEqual(null);
  });

  it("should subscribe when mounting", () => {
    const shortcut = new Shortcut();
    const name = "Foo";
    const handler = () => {};

    shortcut.props = { name, handler };
    shortcut.context = { shortcuts: { subscribe: jest.fn() } };

    const subscribe = jest.spyOn(shortcut.context.shortcuts, "subscribe");

    shortcut.componentWillMount();

    expect(subscribe).toHaveBeenCalledWith(name, handler);
  });

  it("should unsubscribe when unmounting", () => {
    const shortcut = new Shortcut();
    const name = "Foo";
    const handler = () => {};

    shortcut.context = { shortcuts: { unsubscribe: jest.fn() } };
    shortcut.name = name;
    shortcut.handler = handler;

    const unsubscribe = jest.spyOn(shortcut.context.shortcuts, "unsubscribe");

    shortcut.componentWillUnmount();

    expect(unsubscribe).toHaveBeenCalledWith(name, handler);
  });

  it("should unsubscribe the same attributes which were subscribed", () => {
    const shortcut = new Shortcut();
    const name1 = "Foo";
    const handler1 = () => {};
    const name2 = "Bar";
    const handler2 = () => {};

    shortcut.context = {
      shortcuts: { subscribe: jest.fn(), unsubscribe: jest.fn() }
    };

    const subscribe = jest.spyOn(shortcut.context.shortcuts, "subscribe");
    const unsubscribe = jest.spyOn(shortcut.context.shortcuts, "unsubscribe");

    shortcut.props = { name: name1, handler: handler1 };
    shortcut.componentWillMount();

    expect(subscribe).toHaveBeenCalledWith(name1, handler1);

    shortcut.props = { name: name2, handler: handler2 };
    shortcut.componentWillUnmount();

    expect(unsubscribe).toHaveBeenCalledWith(name1, handler1);
  });
});
