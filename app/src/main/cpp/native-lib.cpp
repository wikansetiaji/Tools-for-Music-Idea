#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_valerysa_toolsformusicidea_MainActivity_apiKeyFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string key = "72b562e0817c403396516d4e3bae49fb";
    return env->NewStringUTF(key.c_str());
}
