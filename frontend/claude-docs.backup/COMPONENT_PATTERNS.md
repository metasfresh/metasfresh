# Component Patterns

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Class vs functional components, containers, widgets**

## Key Insight: Mixed Component Styles

**This is React 16.14.0** - The codebase uses a **mix of class components and functional components with hooks**. Many core components (like MasterWindow) are class-based `PureComponent` instances.

## Class Component Pattern (Still Widely Used)

```javascript
// components/app/MasterWindow.js
import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';

export default class MasterWindow extends PureComponent {
  state = {
    newRow: false,
    modalTitle: null,
    dropzoneFocused: false,
  };

  componentDidUpdate(prevProps) {
    const { master } = this.props;

    if (master.docId !== prevProps.master.docId) {
      this.setState({ newRow: false });
    }
  }

  handleAction = () => {
    this.setState({ modalTitle: 'New Title' });
  };

  render() {
    const { master, children } = this.props;
    const { modalTitle } = this.state;

    return (
      <div className="master-window">
        <h1>{modalTitle}</h1>
        {children}
      </div>
    );
  }
}

MasterWindow.propTypes = {
  master: PropTypes.object.isRequired,
  children: PropTypes.node,
};
```

## Functional Component with Hooks

```javascript
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import PropTypes from 'prop-types';

const MyComponent = ({ title, onClose }) => {
  const [isOpen, setIsOpen] = useState(false);
  const data = useSelector(state => state.myData);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchData());

    return () => {
      dispatch(cleanup());
    };
  }, [dispatch]);

  const handleClick = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div>
      <h1>{title}</h1>
      <button onClick={handleClick}>Toggle</button>
      {isOpen && <div>Content</div>}
    </div>
  );
};

MyComponent.propTypes = {
  title: PropTypes.string.isRequired,
  onClose: PropTypes.func
};

export default MyComponent;
```

## Container Pattern (Redux Connected)

**Naming**: Containers end with `Container` suffix.

```javascript
// containers/MasterWindowContainer.js
import { connect } from 'react-redux';
import { fetchData, updateData } from '../actions/WindowActions';
import MasterWindow from '../components/app/MasterWindow';

const mapStateToProps = (state) => ({
  master: state.windowHandler.master,
  loading: state.windowHandler.showSpinner
});

const mapDispatchToProps = (dispatch) => ({
  onFetch: () => dispatch(fetchData()),
  onUpdate: (id, value) => dispatch(updateData(id, value))
});

export default connect(mapStateToProps, mapDispatchToProps)(MasterWindow);
```

## Widget Pattern

Widgets are input components for forms with a standard interface:

```javascript
// components/widget/Lookup/Lookup.js
import React from 'react';
import PropTypes from 'prop-types';

const Lookup = ({
  value,
  onChange,
  readonly,
  mandatory,
  validStatus
}) => {
  const handleChange = (selectedValue) => {
    onChange(selectedValue);
  };

  return (
    <div
      className={`
        input-lookup
        ${mandatory ? 'input-mandatory' : ''}
        ${validStatus?.valid === false ? 'input-error' : ''}
      `}
    >
      {/* Widget implementation */}
    </div>
  );
};

Lookup.propTypes = {
  value: PropTypes.any,
  onChange: PropTypes.func.isRequired,
  readonly: PropTypes.bool,
  mandatory: PropTypes.bool,
  validStatus: PropTypes.shape({
    valid: PropTypes.bool,
    reason: PropTypes.string
  })
};

export default Lookup;
```

### Widget Terminology

| Term | Description |
|------|-------------|
| **MasterWidget** | Field with label wrapper |
| **RawWidget** | Pure input component without label |

## Component Design Best Practices

1. **Understand class vs functional** - Core components are class-based
2. **Keep components small** - Single responsibility principle
3. **Prop types for validation** - Use PropTypes consistently
4. **Avoid inline functions in render** - Performance impact
5. **PureComponent for class components** - Prevents unnecessary re-renders

## When Adding New Features

1. Identify which reducer manages the state
2. Create plain action creator functions (not `createAction`)
3. Update reducer using `immutability-helper` patterns
4. Decide: class component or functional component
5. Wire up with Redux (connect HOC or hooks)
6. Add API calls to `api/` folder
7. Write tests in `__tests__/`

## When Modifying Styles

1. Check existing CSS in `assets/css/`
2. Use existing Bootstrap 4 classes when possible
3. Follow BEM naming convention for custom classes
4. Run `yarn stylelint` to validate
5. Test responsive behavior (mobile/tablet/desktop)
