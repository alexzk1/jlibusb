#pragma once
#include "../libusb-1.0/libusb.h"
#include <string>

#ifdef __cplusplus
extern "C" {
#endif
void LIBUSB_CALL libusb_hack_fd(int fd);
void libusb_hack_path(const char* p);
#ifdef __cplusplus
}
#endif


inline std::string& usb_stored_path()
{
    static std::string path;
    return path;
}

inline void setUsbPath(const std::string& path)
{
   usb_stored_path() = path;
   libusb_hack_path(usb_stored_path().c_str());
}
