import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getMessageOne = (params) => {
    return getRequest('/message/getOne', params)
}
export const getMessageList = (params) => {
    return getRequest('/message/getByPage', params)
}
export const getMessageCount = (params) => {
    return getRequest('/message/count', params)
}
export const addMessage = (params) => {
    return postRequest('/message/insert', params)
}
export const editMessage = (params) => {
    return postRequest('/message/update', params)
}
export const addOrEditMessage = (params) => {
    return postRequest('/message/insertOrUpdate', params)
}
export const deleteMessage = (params) => {
    return postRequest('/message/delByIds', params)
}