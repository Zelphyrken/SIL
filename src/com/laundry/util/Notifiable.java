package com.laundry.util;

import com.laundry.model.Pesanan;

/** Kontrak notifikasi yang saat ini diwujudkan dengan pembukaan tautan WhatsApp. */
public interface Notifiable {
    void kirimNotifikasi(Pesanan pesanan) throws Exception;
}
