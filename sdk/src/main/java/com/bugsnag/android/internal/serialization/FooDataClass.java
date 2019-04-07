package com.bugsnag.android.internal.serialization;

/**
 * The sole responsibility of FooDataClass is to hold data. It is NOT part of our public API by
 * default, and does not require any specific knowledge about the notifier or serialization.
 *
 * Another benefit of this approach is that the class can be immutable, and therefore thread safe.
 *
 * Note that it would be possible to add mutable properties if required due to performance reasons.
 */
final class FooDataClass {

    final String str;
    final BarDataClass bar;

    FooDataClass(String str, BarDataClass bar) {
        this.str = str;
        this.bar = bar;
    }
}
