import React from 'react';
import PropTypes from 'prop-types';
import { render, screen, waitFor } from '@testing-library/react';
import { useQuery } from '../../hooks/useQuery';

/**
 * Callers gate rendering on isPending.
 * If an enabled query reports isPending=false on its first render, the gated child is mounted, then unmounted
 * (pending flips to true in the effect) and mounted again, running its mount effects twice.
 */
const Probe = ({ queryFn, isEnabled, renders }) => {
  const { isPending } = useQuery({ queryFn, enabled: isEnabled });
  renders.push(isPending);
  return <div data-testid="state">{isPending ? 'pending' : 'idle'}</div>;
};

Probe.propTypes = {
  queryFn: PropTypes.func.isRequired,
  isEnabled: PropTypes.bool,
  renders: PropTypes.array.isRequired,
};

describe('useQuery initial pending state', () => {
  it('an enabled query is pending from its very first render (no pending=false -> true flip)', async () => {
    const renders = [];
    render(<Probe queryFn={() => Promise.resolve('x')} isEnabled={true} renders={renders} />);

    expect(renders[0]).toBe(true);
    await waitFor(() => expect(screen.getByTestId('state').textContent).toBe('idle'));
    // never goes false -> true -> false
    expect(renders.indexOf(false)).toBeGreaterThan(renders.lastIndexOf(true));
  });

  it('a disabled query is not pending and does not call the queryFn', () => {
    const renders = [];
    const queryFn = jest.fn(() => Promise.resolve('x'));
    render(<Probe queryFn={queryFn} isEnabled={false} renders={renders} />);

    expect(renders).toEqual([false]);
    expect(queryFn).not.toHaveBeenCalled();
  });
});
