/*
 * Copyright 2016 The LmdbJava Project, http://lmdbjava.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lmdbjava;

import static jnr.ffi.LibraryLoader.create;
import jnr.ffi.Pointer;
import static jnr.ffi.Runtime.getRuntime;
import jnr.ffi.Struct;
import jnr.ffi.Struct.size_t;
import jnr.ffi.Struct.u_int32_t;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.IntByReference;
import jnr.ffi.byref.NativeLongByReference;
import jnr.ffi.byref.PointerByReference;

/**
 * JNR-FFI interface to LMDB.
 * <p>
 * For performance reasons pointers are used rather than structs.
 */
final class Library {

  private static final String LIB_NAME = "lmdb";
  static final Lmdb LIB;
  static final jnr.ffi.Runtime RUNTIME;

  static {
    LIB = create(Lmdb.class).load(LIB_NAME);
    RUNTIME = getRuntime(LIB);
  }

  private Library() {
  }

  @SuppressWarnings("all")
  public static final class MDB_envinfo extends Struct {

    public final Pointer f0_me_mapaddr;
    public final size_t f1_me_mapsize;
    public final size_t f2_me_last_pgno;
    public final size_t f3_me_last_txnid;
    public final u_int32_t f4_me_maxreaders;
    public final u_int32_t f5_me_numreaders;

    public MDB_envinfo(jnr.ffi.Runtime runtime) {
      super(runtime);
      this.f0_me_mapaddr = new Pointer();
      this.f1_me_mapsize = new size_t();
      this.f2_me_last_pgno = new size_t();
      this.f3_me_last_txnid = new size_t();
      this.f4_me_maxreaders = new u_int32_t();
      this.f5_me_numreaders = new u_int32_t();
    }
  }

  @SuppressWarnings("all")
  public static final class MDB_stat extends Struct {

    public final u_int32_t f0_ms_psize;
    public final u_int32_t f1_ms_depth;
    public final size_t f2_ms_branch_pages;
    public final size_t f3_ms_leaf_pages;
    public final size_t f4_ms_overflow_pages;
    public final size_t f5_ms_entries;

    public MDB_stat(jnr.ffi.Runtime runtime) {
      super(runtime);
      this.f0_ms_psize = new u_int32_t();
      this.f1_ms_depth = new u_int32_t();
      this.f2_ms_branch_pages = new size_t();
      this.f3_ms_leaf_pages = new size_t();
      this.f5_ms_entries = new size_t();
      this.f4_ms_overflow_pages = new size_t();
    }
  }

  @SuppressWarnings("all")
  public interface Lmdb {

    void mdb_cursor_close(@In Pointer cursor);

    int mdb_cursor_count(@In Pointer cursor, NativeLongByReference countp);

    int mdb_cursor_del(@In Pointer cursor, int flags);

    int mdb_cursor_get(@In Pointer cursor, Pointer k, @Out Pointer v,
                       int cursorOp);

    int mdb_cursor_open(@In Pointer txn, @In Pointer dbi,
                        PointerByReference cursorPtr);

    int mdb_cursor_put(@In Pointer cursor, @In Pointer key, @In Pointer data,
                       int flags);

    int mdb_cursor_renew(@In Pointer txn, @In Pointer cursor);

    void mdb_dbi_close(@In Pointer env, @In Pointer dbi);

    int mdb_dbi_flags(@In Pointer txn, @In Pointer dbi, int flags);

    int mdb_dbi_open(@In Pointer txn, @In String name, int flags,
                     @In Pointer dbiPtr);

    int mdb_del(@In Pointer txn, @In Pointer dbi, @In Pointer key,
                @In Pointer data);

    int mdb_drop(@In Pointer txn, @In Pointer dbi, int del);

    void mdb_env_close(@In Pointer env);

    int mdb_env_copy2(@In Pointer env, @In String path, int flags);

    int mdb_env_create(PointerByReference envPtr);

    int mdb_env_get_fd(@In Pointer env, @In Pointer fd);

    int mdb_env_get_flags(@In Pointer env, int flags);

    int mdb_env_get_maxkeysize(@In Pointer env);

    int mdb_env_get_maxreaders(@In Pointer env, int readers);

    int mdb_env_get_path(@In Pointer env, String path);

    int mdb_env_info(@In Pointer env, @Out MDB_envinfo info);

    int mdb_env_open(@In Pointer env, @In String path, int flags, int mode);

    int mdb_env_set_flags(@In Pointer env, int flags, int onoff);

    int mdb_env_set_mapsize(@In Pointer env, long size);

    int mdb_env_set_maxdbs(@In Pointer env, int dbs);

    int mdb_env_set_maxreaders(@In Pointer env, int readers);

    int mdb_env_stat(@In Pointer env, @Out MDB_stat stat);

    int mdb_env_sync(@In Pointer env, int f);

    int mdb_get(@In Pointer txn, @In Pointer dbi, @In Pointer key,
                @Out Pointer data);

    int mdb_put(@In Pointer txn, @In Pointer dbi, @In Pointer key,
                @In Pointer data,
                int flags);

    int mdb_reader_check(@In Pointer env, int dead);

    String mdb_strerror(int rc);

    void mdb_txn_abort(@In Pointer txn);

    int mdb_txn_begin(@In Pointer env, @In Pointer parentTx, int flags,
                      Pointer txPtr);

    int mdb_txn_commit(@In Pointer txn);

    Pointer mdb_txn_env(@In Pointer txn);

    long mdb_txn_id(@In Pointer txn);

    int mdb_txn_renew(@In Pointer txn);

    void mdb_txn_reset(@In Pointer txn);

    Pointer mdb_version(IntByReference major, IntByReference minor,
                        IntByReference patch);

  }
}
