/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2024 metas GmbH
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

let audioCtx = null; // lazy to allow jest testing

export const beep = ({ beepFrequency, beepDurationMillis, vibrateMillis, beepVolume }) => {
  if (!audioCtx) {
    audioCtx = new AudioContext();
  }

  // console.trace('beep', { beepFrequency, beepDurationMillis, beepVolume, vibrateMillis, audioCtx });

  try {
    //
    // Beep
    if (beepFrequency > 0 && beepDurationMillis > 0 && beepVolume > 0) {
      //
      // Volume (connected to audio destination)
      const volume = audioCtx.createGain();
      volume.connect(audioCtx.destination);
      volume.gain.value = beepVolume; // 0...1

      //
      // Oscillator (connected to Volume)
      const oscillator = audioCtx.createOscillator();
      oscillator.type = 'square';
      oscillator.frequency.setValueAtTime(beepFrequency, audioCtx.currentTime); // value in hertz
      //oscillator.connect(audioCtx.destination);
      oscillator.connect(volume);

      //
      // Start it
      oscillator.start();
      oscillator.stop(audioCtx.currentTime + beepDurationMillis / 1000);
    }

    //
    // Vibrate
    if (vibrateMillis > 0) {
      navigator.vibrate(vibrateMillis);
    }
  } catch (error) {
    console.log('Failed beeping', { error });
  }
};
