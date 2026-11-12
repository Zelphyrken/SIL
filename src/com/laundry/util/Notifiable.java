package com.laundry.util;

import com.laundry.model.Pesanan;

public interface Notifiable {
    void kirimNotifikasi(Pesanan pesanan) throws Exception;
}
