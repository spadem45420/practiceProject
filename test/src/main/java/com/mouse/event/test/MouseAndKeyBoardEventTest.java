package com.mouse.event.test;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;
/**
 * @author javaQuery
 * Global Keyboard Listener
 */
public class MouseAndKeyBoardEventTest implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener{

    /* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        /* Terminate program when one press ESCAPE */
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
        }
    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) {
        try {
            /* Register jNativeHook */
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            /* Its error */
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        /* Construct the example object and initialze native hook. */
        GlobalScreen.addNativeKeyListener(new MouseAndKeyBoardEventTest());
        GlobalScreen.addNativeMouseListener(new MouseAndKeyBoardEventTest());
    }

	public void nativeMouseClicked(NativeMouseEvent paramNativeMouseEvent) {
		System.out.println("nativeMouseClicked");
	}

	public void nativeMousePressed(NativeMouseEvent paramNativeMouseEvent) {
		System.out.println("nativeMousePressed");
	}

	public void nativeMouseReleased(NativeMouseEvent paramNativeMouseEvent) {
		System.out.println("nativeMouseReleased");
	}

	public void nativeMouseMoved(NativeMouseEvent paramNativeMouseEvent) {
		System.out.println("nativeMouseMoved");
	}

	public void nativeMouseDragged(NativeMouseEvent paramNativeMouseEvent) {
		System.out.println("nativeMouseDragged");
	}
}