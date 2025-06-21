<template>
<div>
    <Card>
        <div slot="title">
            <div class="edit-head">
                <a @click="close" class="back-title">
                    <Icon type="ios-arrow-back" />返回
                </a>
                <div class="head-name">添加</div>
                <span></span>
                <a @click="close" class="window-close">
                    <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
                </a>
            </div>
        </div>
        <Form ref="form" :model="form" :label-width="100" :rules="formValidate" label-position="left">
            <FormItem label="课程" prop="curriculumId">
                <Select v-model="form.curriculumId" clearable placeholder="请选择课程..." style="width:570px">
                    <Option v-for="(item,index) in curriculumList" :key="index" :value="item.id">{{ item.title }}</Option>
                </Select>
            </FormItem>
            <FormItem label="学生" prop="userId">
                <Select v-model="form.userId" clearable placeholder="请选择学生..." style="width:570px">
                    <Option v-for="(item,index) in userList" :key="index" :value="item.id">{{ item.nickname }}</Option>
                </Select>
            </FormItem>
            <FormItem label="成绩" prop="grade">
                <InputNumber v-model="form.grade" min="0" max="5000000" placeholder="请输入成绩..." style="width:570px"></InputNumber>
            </FormItem>
            <FormItem label="备注" prop="reark">
                <Input v-model="form.reark" type="textarea" :rows="4" clearable maxlength="240" show-word-limit placeholder="请输入备注..." style="width:570px" />
            </FormItem>
            <Form-item class="br">
                <Button @click="handleSubmit" :loading="submitLoading" type="primary">提交并保存</Button>
                <Button @click="handleReset">重置</Button>
                <Button type="dashed" @click="close">关闭</Button>
            </Form-item>
        </Form>
    </Card>
</div>
</template>

<script>
import {
    addAchievement,
    getCurriculumList,
    getUserList
} from "./api.js";
export default {
    name: "add",
    components: {},
    data() {
        return {
            submitLoading: false,
            form: {
                curriculumId: "",
                curriculumName: "",
                userId: "",
                userName: "",
                grade: 0,
                reark: "",
            },
            formValidate: {},
            curriculumList: [],
            userList: []
        };
    },
    methods: {
        init() {
            this.getCurriculumListFx();
            this.getUserListFx();
        },
        getCurriculumListFx() {
            var that = this;
            that.curriculumList = [];
            getCurriculumList().then(res => {
                if (res.success) {
                    that.curriculumList = res.result;
                }
            })
        },
        getUserListFx() {
            var that = this;
            that.userList = [];
            getUserList().then(res => {
                if (res.success) {
                    that.userList = res.result;
                }
            })
        },
        handleReset() {
            this.$refs.form.resetFields();
        },
        handleSubmit() {
            this.$refs.form.validate(valid => {
                if (valid) {
                    addAchievement(this.form).then(res => {
                        this.submitLoading = false;
                        if (res.success) {
                            this.$Message.success("操作成功");
                            this.submited();
                        }
                    });
                }
            });
        },
        close() {
            this.$emit("close", true);
        },
        submited() {
            this.$emit("submited", true);
        }
    },
    mounted() {
        this.init();
    }
};
</script>

<style lang="less">
.edit-head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    position: relative;

    .back-title {
        color: #515a6e;
        display: flex;
        align-items: center;
    }

    .head-name {
        display: inline-block;
        height: 20px;
        line-height: 20px;
        font-size: 16px;
        color: #17233d;
        font-weight: 500;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .window-close {
        z-index: 1;
        font-size: 12px;
        position: absolute;
        right: 0px;
        top: -5px;
        overflow: hidden;
        cursor: pointer;

        .ivu-icon-ios-close {
            color: #999;
            transition: color .2s ease;
        }
    }

    .window-close .ivu-icon-ios-close:hover {
        color: #444;
    }
}
</style>
