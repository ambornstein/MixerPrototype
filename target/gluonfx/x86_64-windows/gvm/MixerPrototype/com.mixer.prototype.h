#ifndef __COM_MIXER_PROTOTYPE_H
#define __COM_MIXER_PROTOTYPE_H

#include <graal_isolate.h>


#if defined(__cplusplus)
extern "C" {
#endif

int run_main(int argc, char** argv);

void vmLocatorSymbol(graal_isolatethread_t* thread);

#if defined(__cplusplus)
}
#endif
#endif
