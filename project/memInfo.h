/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class memInfo */

#ifndef _Included_memInfo
#define _Included_memInfo
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     memInfo
 * Method:    read
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_memInfo_read
  (JNIEnv *, jobject);

/*
 * Class:     memInfo
 * Method:    getTotal
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_memInfo_getTotal
  (JNIEnv *, jobject);

/*
 * Class:     memInfo
 * Method:    getUsed
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_memInfo_getUsed
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
